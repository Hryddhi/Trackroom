from django.apps import AppConfig
from django.db.models.signals import post_migrate


class ModulesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'modules'

    def ready(self):
        from .models import ContentMediaType

        def create_content_media_type(sender,**kwargs):
            for media_type in ContentMediaType.CONTENT_MEDIA_TYPE_CHOICES:
                if not ContentMediaType.objects.filter(media_type=media_type).exists():
                    ContentMediaType.objects.create(media_type=media_type)

        post_migrate.connect(create_content_media_type, sender=self)
