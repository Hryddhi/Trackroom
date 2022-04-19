from django.db import models

from accounts.models import Account
from classrooms.models import Classroom
from notifications.models import Notification


class QuizManager(models.Manager):
    def create(self, *args, **kwargs):
        quiz = super(QuizManager, self).create(*args, **kwargs)
        Notification.create_notification_for(quiz)
        return quiz


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

    QuizObject = QuizManager()


class Question(models.Model):
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    question = models.TextField()
    relative_index = models.IntegerField(default=0)
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


class OptionManager(models.Manager):
    def create(self, *args, **kwargs):
        option = super(OptionManager, self).create(*args, **kwargs)
        option.set_relative_index()
        return option


class Option(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    option = models.CharField(max_length=255)
    relative_index = models.IntegerField(default=0)
    is_correct = models.BooleanField(default=False)
    OptionObject = OptionManager()

    @property
    def label(self):
        return f"Option{self.relative_index}"

    def set_relative_index(self):
        rq_qs = Option.OptionObject.filter(question=self.question)
        relative_index = rq_qs.count() if rq_qs.exists() else 1
        self.relative_index = relative_index
        self.save()


class AssignedQuizManager(models.Manager):
    def create(self, *args, **kwargs):
        question_qs = Question.QuestionObject.filter(quiz=kwargs['quiz'])
        question_count = question_qs.count() if question_qs.exists() else "-"
        kwargs.update({'grade': f"-/{question_count}"})
        assigned_quiz = super(AssignedQuizManager, self).create(*args, **kwargs)
        return assigned_quiz


class AssignedQuiz(models.Model):
    subscriber = models.ForeignKey(Account, on_delete=models.CASCADE)
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    has_attended = models.BooleanField(default=False)
    grade = models.CharField(max_length=20, null=True)
    date_created = models.DateTimeField(auto_now_add=True)

    AssignedQuizObject = AssignedQuizManager()

    class Meta:
        unique_together = ('subscriber', 'quiz')

    def auto_grade(self):
        if self.grade.startswith("-"):
            question_count = self.grade.split("/")[1]
            correct_answer_qs = Answer.AnswerObject.filter(attended_quiz__quiz=self.quiz, selected_option__is_correct=True)
            correct_answer_count = correct_answer_qs.count() if correct_answer_qs.exists() else 0
            self.has_attended = True
            self.grade = f"{correct_answer_count}/{question_count}"
            self.save()

    @staticmethod
    def assign_this_quiz_to_respective_subscribers(quiz):
        for subscriber in quiz.classroom.subscribers:
            aq = AssignedQuiz.AssignedQuizObject.create(
                subscriber=subscriber,
                quiz=quiz)


class Answer(models.Model):
    attended_quiz = models.ForeignKey(AssignedQuiz, on_delete=models.CASCADE)
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    selected_option = models.ForeignKey(Option, on_delete=models.CASCADE)

    class Meta:
        unique_together = ('attended_quiz', 'question')

    AnswerObject = models.Manager()

