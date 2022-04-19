from django.apps import AppConfig
from django.db.models.signals import post_migrate

class CommentsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'comments'

    def ready(self):
        from accounts.models import Account
        from modules.models import Module
        from .models import Comment

        def create_test_comment(sender, **kwargs):
            if not Comment.CommentObject.filter(module=1).exists():
                module = Module.ModuleObject.get(pk=1)
                creator = Account.objects.get(pk=4)
                Comment.CommentObject.create(
                    module=module,
                    creator=creator,
                    comment="This is test comment (1)"
                )
                Comment.CommentObject.create(
                    module=module,
                    creator=creator,
                    comment="This is test comment (2)"
                )

        post_migrate.connect(create_test_comment, sender=self)
