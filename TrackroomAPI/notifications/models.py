from django.utils import timezone
from django.db import models

from accounts.models import Account
from classrooms.models import Classroom, get_list_of_joined_classroom, get_list_of_created_classroom
# Create your models here.


class NotificationType(models.Model):
    NOTIFICATION_TYPE_CHOICES = ["Module", "Quiz", "Comment"]
    notification_type = models.CharField(unique=True, primary_key=True)


class NotificationManger(models.Manager):
    def create(self, *args, **kwargs):
        notification = super(NotificationManger, self).create(*args, **kwargs)
        return notification


class Notification(models.Model):
    related_classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    notification_type = models.ForeignKey(NotificationType, on_delete=models.CASCADE, editable=False)
    date_created = models.DateTimeField()
    message = models.TextField(default="")

    NotificationObject = NotificationManger()

    def set_new_post_creation_message(self, post):
        message = f"A new {self.notification_type} {post.title} has been posted in the classroom {self.related_classroom}."
        self.message = message
        self.save()

    # def set_new_comment_creation_message(self, comment):
    #      message = f"A new comment has been made by {comment.creator.profile.username}({comment.creator.email}) " \
    #                f"under your post {comment.module.title}"
    #      self.message = message
    #      self.save()

    # @staticmethod
    # def get_related_notification_of(account):
    #     qs_subs = Notification.NotificationObject.filter(
    #         related_classroom__in=get_list_of_joined_classroom(account),
    #         notification_type=
    #     ).order_by('-date_created')
    #     qs_crt =
