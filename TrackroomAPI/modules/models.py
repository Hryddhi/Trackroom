import source
from source.utils import content_material_file_location

from django.db import models

from classrooms.models import Classroom
from notifications.models import Notification


class ContentMediaType(models.Model):
    CONTENT_MEDIA_TYPE_CHOICES = ["PDF", "Image", "Video"]
    media_type = models.CharField(
        max_length=30, unique=True, primary_key=True)
    objects = models.Manager()


class ModuleManager(models.Manager):
    def create(self, *args, **kwargs):
        module = super(ModuleManager, self).create(*args, **kwargs)
        Notification.create_notification_for(module)
        return module


class Module(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE)
    title = models.CharField(max_length=100)
    description = models.TextField(blank=True)
    date_created = models.DateTimeField(auto_now_add=True)

    ModuleObject = ModuleManager()

    class Meta:
        unique_together = ('classroom', 'title')

    @property
    def content_material(self):
        content_material = ContentMaterial.ContentMaterialObject.filter(module=self)
        return [files.file for files in content_material]

    @staticmethod
    def get_created_module_from(classroom_list):
        if '__iter__' not in dir(classroom_list):
            classroom_list = [classroom_list]
        return Module.ModuleObject.filter(
            classroom__in=classroom_list)

    def __str__(self):
        return f"{self.classroom.__str__()}'s {self.title}"


class ContentMaterialManager(models.Manager):

    def create(self, *args, **kwargs):
        content_material = super(ContentMaterialManager, self).create(*args, **kwargs)
        content_material.set_label()
        return content_material


class ContentMaterial(models.Model):
    module = models.ForeignKey(Module, on_delete=models.CASCADE, blank=False, null=False)
    label = models.CharField(max_length=255, null=True, default=None)
    file = models.FileField(upload_to=content_material_file_location)
    file_type = models.ForeignKey(ContentMediaType, on_delete=models.PROTECT)

    ContentMaterialObject = ContentMaterialManager()

    def get_next_content_material_position(self):
        rm_qs = ContentMaterial.ContentMaterialObject.filter(module=self.module)
        return rm_qs.count() if rm_qs.exists() else 1

    def set_label(self):
        self.label = f"{self.module.classroom.title}_{self.module.title} {self.get_next_content_material_position()}"
        self.save()

    def __str__(self):
        return self.label if self.label is not None else "no label yet"
