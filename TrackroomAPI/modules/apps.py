from django.apps import AppConfig
from django.db.models.signals import post_migrate


class ModulesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'modules'

    def ready(self):
        from classrooms.models import Classroom
        from .models import ContentMediaType, ContentMaterial, Module
        from notifications.models import Notification, ModelType

        def create_content_media_type(sender, **kwargs):
            for media_type in ContentMediaType.CONTENT_MEDIA_TYPE_CHOICES:
                if not ContentMediaType.objects.filter(media_type=media_type).exists():
                    ContentMediaType.objects.create(media_type=media_type)

        def create_test_module(sender, **kwargs):
            if not Module.ModuleObject.filter(classroom=3, title='Test Module 1'):
                classroom = Classroom.ClassroomObject.get(pk=3)
                module = Module.ModuleObject.create(
                    classroom=classroom,
                    title='Test Module 1',
                    description='This is the description of Test Module 1 in Test Classroom 1'
                )
                ContentMaterial.ContentMaterialObject.create(
                    module=module,
                    file='Content/1/1/Test Image.JPEG',
                    file_type=ContentMediaType.objects.get(pk="Image")
                )
                Notification.NotificationObject.create(
                    related_classroom=classroom,
                    related_model_type=ModelType.objects.get(pk=ModelType.MODULE),
                    date_created=module.date_created,
                    related=module
                )

            if not Module.ModuleObject.filter(classroom=3, title='Test Module 2'):
                classroom = Classroom.ClassroomObject.get(pk=3)
                module = Module.ModuleObject.create(
                    classroom=classroom,
                    title='Test Module 2',
                    description='This is the description of Test Module 2 in Test Classroom 1'
                )
                ContentMaterial.ContentMaterialObject.create(
                    module=module,
                    file='Content/1/1/Test PDF.pdf',
                    file_type=ContentMediaType.objects.get(pk="PDF")
                )
                Notification.NotificationObject.create(
                    related_classroom=classroom,
                    related_model_type=ModelType.objects.get(pk=ModelType.MODULE),
                    date_created=module.date_created,
                    related=module
                )

        post_migrate.connect(create_content_media_type, sender=self)
        post_migrate.connect(create_test_module, sender=self)
