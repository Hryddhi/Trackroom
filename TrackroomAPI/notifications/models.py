from django.utils import timezone
from django.db import models

from accounts.models import Account
from classrooms.models import Classroom
# Create your models here.


class NotificationType(models.Model):
    NOTIFICATION_TYPE_CHOICES = ["Post", "Comment"]
    notification_type = models.CharField(unique=True, primary_key=True)


class NotificationManger(models.Manager):
    def create(self, *args, **kwargs):
        date = timezone.now().strftime('%d-%m-%Y')
        kwargs.update({'date': date})
        notification = super(NotificationManger, self).create(*args, **kwargs)
        return notification


class Notification(models.Model):
    related_account = models.ForeignKey(Account, on_delete=models.CASCADE, editable=False)
    related_classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, editable=False)
    notification_type = models.ForeignKey(NotificationType, on_delete=models.CASCADE, editable=False)
    date_created = models.CharField()
    message = models.TextField()


    def set_new_post_creation_message(self, module):
        message = f"A new module {module.title} has been posted in the classroom."
        self.message = message
        self.save()


    def set_new_comment_creation_message(self, comment):
         message = f"A new comment has been made by {comment.creator.profile.username}({comment.creator.email}) " \
                   f"under your post {comment.module.title}"
         self.message = message
         self.save()


    def get_new_reply_creation_message(self, comment):
        pass