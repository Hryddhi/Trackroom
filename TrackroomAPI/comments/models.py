from django.db import models

from accounts.models import Account
from modules.models import Module
from notifications.models import Notification


class CommentManager(models.Manager):
    def create(self, *args, **kwargs):
        comment = super(CommentManager, self).create(*args, **kwargs)
        Notification.create_notification_for(comment)
        return comment


class Comment(models.Model):
    creator = models.ForeignKey(Account, on_delete=models.CASCADE)
    module = models.ForeignKey(Module, on_delete=models.CASCADE)
    comment = models.TextField()
    date_created = models.DateTimeField(auto_now_add=True)

    CommentObject = CommentManager()
