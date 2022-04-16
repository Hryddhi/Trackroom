from django.utils import timezone
from django.db import models

from accounts.models import Account
from classrooms.models import Classroom, get_list_of_joined_classroom, get_list_of_created_classroom
# Create your models here.


class ModelType(models.Model):
    MODULE, QUIZ, COMMENT = "Module", "Quiz", "Comment"
    MODEL_TYPE_CHOICES = [MODULE, QUIZ, COMMENT]
    model_type = models.CharField(
        max_length=30, unique=True, primary_key=True)
    objects = models.Manager()


class NotificationManger(models.Manager):
    def create(self, *args, **kwargs):
        related = kwargs.pop('related')
        notification = super(NotificationManger, self).create(*args, **kwargs)
        if notification.related_model_type.pk in [ModelType.MODULE, ModelType.QUIZ]:
            notification.set_new_post_creation_message(related)
        elif notification.related_model_type.pk == ModelType.COMMENT:
            notification.set_new_comment_creation_message(related)
        return notification


class Notification(models.Model):
    related_classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    related_model_type = models.ForeignKey(ModelType, on_delete=models.CASCADE, editable=False)
    date_created = models.DateTimeField()
    message = models.TextField(default="")

    NotificationObject = NotificationManger()

    def set_new_post_creation_message(self, post):
        message = f"A new {self.related_model_type.pk} '{post.title}' has been posted in the classroom {self.related_classroom}."
        self.message = message
        self.save()

    def set_new_comment_creation_message(self, comment):
        pass
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
