from django.db import models

from accounts.models import Account
from classrooms.models import Classroom


class Quiz(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    title = models.CharField(max_length=255)
    start_time = models.CharField(max_length=50)
    end_time = models.CharField(max_length=50)
    description = models.TextField()
    date_created = models.DateTimeField(auto_now_add=True)

    class Meta:
        unique_together = ('classroom', 'title')

    def __str__(self):
        return self.title

    QuizObject = models.Manager()


class Question(models.Model):
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    question = models.TextField()
    QuestionObject = models.Manager()

    @property
    def options(self):
        options = Option.OptionObject.filter(question=self)
        return [o.option for o in options]

    @property
    def correct_option(self):
        options = Option.OptionObject.filter(question=self)
        for o in options:
            if o.is_correct is True:
                return o.option

    def __str__(self):
        return self.question


class OptionManger(models.Manager):
    def create(self, *args, **kwargs):
        option = super(OptionManger, self).create(*args, **kwargs)
        option.set_position()
        return option


class Option(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    option = models.CharField(max_length=255)
    position = models.IntegerField(default=0)
    is_correct = models.BooleanField(default=False)
    OptionObject = OptionManger()

    @property
    def label(self):
        return f"Option{self.position}"

    def set_position(self):
        rq_qs = Option.OptionObject.filter(question=self.question)
        position = rq_qs.count() if rq_qs.exists() else 1
        self.position = position
        self.save()


class AssignedQuiz(models.Model):
    subscriber = models.ForeignKey(Account, on_delete=models.CASCADE)
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    has_attended = models.BooleanField()
    grade = models.CharField(max_length=20, default=None, null=True)
    date_created = models.DateTimeField(auto_now_add=True)

    AttendedQuizObject = models.Manager()

    def auto_grade(self):
        if self.grade is None:
            question_qs = Question.QuestionObject.filter(quiz=self.quiz)
            question_count = question_qs.count() if question_qs.exists() else "-"
            if self.has_attended:
                correct_answer_qs = Answer.AnswerObject.filter(attended_quiz__quiz=self, is_correct=True)
                correct_answer_count = correct_answer_qs.count() if correct_answer_qs.exists() else 0
            else:
                correct_answer_count = "-"
            self.grade = f"{correct_answer_count}/{question_count}"
            self.save()


class Answer(models.Model):
    attended_quiz = models.ForeignKey(AssignedQuiz, on_delete=models.CASCADE)
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    selected_option = models.ForeignKey(Option, on_delete=models.CASCADE)
    is_correct = models.BooleanField()

    class Meta:
        unique_together = ('attended_quiz', 'question')

    AnswerObject = models.Manager()
