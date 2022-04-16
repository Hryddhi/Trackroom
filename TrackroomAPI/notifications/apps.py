from django.apps import AppConfig
from django.db.models.signals import post_migrate


class NotificationsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'notifications'

    def ready(self):
        from .models import ModelType

        def create_model_type(sender, **kwargs):
            for model_type in ModelType.MODEL_TYPE_CHOICES:
                if not ModelType.objects.filter(model_type=model_type).exists():
                    ModelType.objects.create(model_type=model_type)

        post_migrate.connect(create_model_type, sender=self)
