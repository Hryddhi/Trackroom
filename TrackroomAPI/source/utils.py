import os
import base64
import io
from PIL import Image
import logging

from django.conf import settings
from django.shortcuts import _get_queryset
from django.core.exceptions import ValidationError

from rest_framework import serializers

from source.exceptions import CustomNotFoundError


def get_object_or_404(klass, *args, **kwargs):

    queryset = _get_queryset(klass)
    try:
        return queryset.get(*args, **kwargs)
    except queryset.model.DoesNotExist:
        model = queryset.model._meta.object_name
        raise CustomNotFoundError(f"{model} not found")


def profile_image_file_location(instance, image):
    image = Image.open(instance.profile_image)
    file_path = f"Profile_image/{instance.pk}.{image.format}"
    return file_path


def content_material_file_location(instance, file):
    _, file_extension = os.path.splitext(file)
    file_path = f"Content/{instance.module.classroom.pk}/{instance.module.pk}/{instance.get_next_content_material_position()}{file_extension}"
    return file_path


def delete_previous_file(path):
    path = settings.BASE_DIR + path
    if os.path.isfile(path):
        print("removing...")
        os.remove(path)


def base64_image_validator(data):
    try:
        decoded_data = base64.b64decode(data.encode('UTF-8'))
        buf = io.BytesIO(decoded_data)
        img = Image.open(buf)
    except Exception as exc:
        raise serializers.ValidationError("Invalid Image.")


def image_validator(data):
    if data.content_type not in ['image/png', 'image/jpeg']:
        raise serializers.ValidationError("Image format not supported.")


def pdf_validator(data):
    if data.content_type != 'application/pdf':
        raise serializers.ValidationError("File Format Not Supported")


def find_file_type(file):
    if (file.content_type).__contains__("image"):
        return "Image"
    elif (file.content_type).__contains__("pdf"):
        return "PDF"
    return None


def sort_post(module, quiz):
    module = []
    quiz = []
    module_count = len(module)
    quiz_count = len(quiz)
    sorted_array = []
    i = 0
    j = 0
    while i < module_count and j < quiz_count:
        if module[i].date_created >= quiz[j].date_created:
            sorted_array.append(module[i])
            i = i + 1
        else:
            sorted_array.append(quiz[i])
            j = j + 1
    while i < module_count:
        sorted_array.append(module[i])
        i = i + 1
    while j < quiz_count:
        sorted_array.append(quiz[i])
        j = j + 1

    pass
