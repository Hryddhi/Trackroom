import os
from django.db import models
from django.conf import settings

from classrooms.models import Classroom


class ContentMediaType(models.Model):
    CONTENT_MEDIA_TYPE_CHOICES = ["PDF", "Image", "Video"]
    media_type = models.CharField(
        max_length=30, unique=True, primary_key=True)
    objects = models.Manager()


class Module(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE)
    title = models.CharField(max_length=100)
    description = models.TextField(blank=True)
    date_created = models.DateTimeField(auto_now_add=True)

    ModuleObject = models.Manager()

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


def content_material_file_location(instance, file):
    _, file_extension = os.path.splitext(file)
    file_path = settings.MEDIA_ROOT + f"/Content/{instance.module.classroom.pk}/{instance.module.pk}/{get_next_content_material_position(instance.module)}.{file_extension}"
    return file_path


def get_next_content_material_position(module):
    rm_qs = ContentMaterial.ContentMaterialObject.filter(module=module)
    return rm_qs.count() if rm_qs.exists() else 1


class ContentMaterial(models.Model):
    module = models.ForeignKey(Module, on_delete=models.CASCADE, blank=False, null=False)
    label = models.CharField(max_length=255, null=True, default=None)
    file = models.FileField(upload_to=content_material_file_location)
    file_type = models.ForeignKey(ContentMediaType, on_delete=models.PROTECT)

    ContentMaterialObject = ContentMaterialManager()

    def set_label(self):
        self.label = f"{self.module.classroom.title}_{self.module.title} {get_next_content_material_position(self.module)}"
        self.save()

    def __str__(self):
        return self.label if self.label is not None else "no label yet"
