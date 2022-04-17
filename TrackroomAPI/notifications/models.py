from django.utils import timezone
from django.db import models

from accounts.models import Account
from classrooms.models import Classroom, get_list_of_joined_classroom, get_list_of_created_classroom
from modules.models import Module
from quizes.models import Quiz


class ModelType(models.Model):
    MODULE, QUIZ, COMMENT = "Module", "Quiz", "Comment"
    MODEL_TYPE_CHOICES = [MODULE, QUIZ, COMMENT]
    model_type = models.CharField(
        max_length=30, unique=True, primary_key=True)
    objects = models.Manager()

    def __str__(self):
        return self.pk


class Notification(models.Model):
    related_classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    related_model_type = models.ForeignKey(ModelType, on_delete=models.CASCADE, editable=False)
    date_created = models.DateTimeField()
    message = models.TextField()

    NotificationObject = models.Manager()

    @staticmethod
    def get_new_post_creation_message(post):
        return f"A new {post.__class__.__name__.lower()} '{post.title}' has been posted in the classroom {post.classroom}."

    @staticmethod
    def get_new_comment_creation_message(comment):
        return ""
    #      message = f"A new comment has been made by {comment.creator.profile.username}({comment.creator.email}) " \
    #                f"under your post {comment.module.title}"
    #      self.message = message
    #      self.save()

    @staticmethod
    def get_related_notification_of(account):
        qs_subs = Notification.NotificationObject.filter(
            related_classroom__in=get_list_of_joined_classroom(account),
            related_model_type__pk__in=[ModelType.MODULE, ModelType.QUIZ]
        )
        qs_crt = Notification.NotificationObject.filter(
            related_classroom__in=get_list_of_created_classroom(account),
            related_model_type__pk='Comment'
        )
        return qs_subs.union(qs_crt).order_by('-date_created')[:10]


def create_notification_for(related):
    type = related.__class__.__name__
    if type in [ModelType.MODULE, ModelType.QUIZ]:
        classroom = related.classroom
        message = Notification.get_new_post_creation_message(related)
    elif type == ModelType.COMMENT:
        classroom = related.module.classroom
        message = Notification.get_new_comment_creation_message(related)

    Notification.NotificationObject.create(
        related_classroom=classroom,
        related_model_type=ModelType.objects.get(pk=type),
        message=message,
        date_created=related.date_created)
