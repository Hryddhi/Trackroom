from django.db import models

from accounts.models import Account
from classrooms.models import Classroom, get_list_of_joined_classroom


class Quiz(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    title = models.CharField(max_length=255)
    date_created = models.DateTimeField(auto_now_add=True)

    class meta:
        unique_together = ('classroom', 'title')

    QuizObject = models.Manager()


class Question(models.Model):
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    question = models.TextField()
    QuestionObject = models.Manager()

    @property
    def options(self):
        options = Option.OptionObject.filter(question=self)
        return [o.label for o in options]


class Option(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    option = models.CharField(max_length=255)
    position = models.IntegerField()
    OptionObject = models.Manager()

    @property
    def label(self):
        return f"Option{self.position}"

    def set_position(self):
        rq_qs = Option.OptionObject.filter(question=self.question)
        position = rq_qs.count() if rq_qs.exists() else 1
        self.position = position
        self.save()


class CorrectOption(models.Model):
    # correct_option = models.
    # models.ForeignKey(limit_choices_to=)
    pass


class AttendedQuiz(models.Model):
    attendee = models.ForeignKey(Account, on_delete=models.CASCADE)

    def quiz_choices(self):
        classroom = get_list_of_joined_classroom(self.attendee)
        return Quiz.QuizObject.filter(classroom__in=classroom)

    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE, limit_choices_to=quiz_choices)
    date_created = models.DateTimeField(auto_now_add=True)


class Answer(models.Model):
    attended_quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    question = models.OneToOneField(Question, on_delete=models.CASCADE)
    # selected_option = models.

    AnswerObject = models.Manager()