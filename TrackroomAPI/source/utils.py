import math
import os
import base64
import io
from PIL import Image

import cv2
import face_recognition

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
    delete_previous_file(file_path)
    return file_path


def content_material_file_location(instance, file):
    _, file_extension = os.path.splitext(file)
    file_path = f"Content/{instance.module.classroom.pk}/{instance.module.pk}/{instance.get_next_content_material_position()}{file_extension}"
    delete_previous_file(file_path)
    return file_path


def delete_previous_file(path):
    path = settings.MEDIA_ROOT + "/" + path
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


def sort_post(modules, quizes, ModuleSerializer, ListQuizSerializer):
    module_count, quiz_count = len(modules), len(quizes)
    i, j = 0 , 0
    sorted_array = []
    while i < module_count and j < quiz_count:
        if modules[i].date_created >= quizes[j].date_created:
            sorted_array.append(ModuleSerializer(modules[i]).data)
            i = i + 1
        else:
            sorted_array.append(ListQuizSerializer(quizes[j]).data)
            j = j + 1
    while i < module_count:
        sorted_array.append(ModuleSerializer(modules[i]).data)
        i = i + 1
    while j < quiz_count:
        sorted_array.append(ListQuizSerializer(quizes[j]).data)
        j = j + 1

    return sorted_array


def get_recommendation_list(pool, container, keys, ClassroomSerializer):
    category_pref = {}
    allowable = {}
    for key in keys:
        value = container.filter(class_category__pk=key).count() * pool.count() / container.count()
        category_pref[key] = round(value, 3)
    category_pref = {k: v for k, v in sorted(category_pref.items(), reverse=True, key=lambda item: item[1])}
    print(category_pref)

    for item in category_pref.keys():
        ratio = category_pref[item] * 7 / sum(category_pref.values())
        ratio = math.ceil(ratio) if ratio >= (int(ratio) + 0.5) else math.floor(ratio)
        value = min(ratio, pool.filter(class_category__pk=item).count())
        allowable[item] = value
    print(allowable)

    recommendations = []
    i = 0
    for item in category_pref:
        qs = pool.filter(class_category__pk=item)[:allowable[item]]
        for classroom in qs:
            recommendations.append(ClassroomSerializer(classroom).data)
            i = i + 1
        pool = pool.exclude(pk__in=[x.pk for x in qs])

    while i<7 and pool.exists():
        print(pool.first())
        classroom = pool.first()
        recommendations.append(ClassroomSerializer(classroom).data)
        i = i + 1
        pool = pool.exclude(pk=classroom.pk)
    # print(recommendations)

    return recommendations


def image_comparator(sample_image, test_image):
    sample_image = settings.BASE_DIR + sample_image

    sample_image = face_recognition.load_image_file(sample_image)
    sample_image = cv2.cvtColor(sample_image, cv2.COLOR_BGR2RGB)

    test_image = face_recognition.load_image_file(test_image)
    test_image = cv2.cvtColor(test_image, cv2.COLOR_BGR2RGB)

    faceLoc = face_recognition.face_locations(test_image)[0]
    cv2.rectangle(test_image, (faceLoc[3], faceLoc[0]),
                  (faceLoc[1], faceLoc[2]),
                  (255, 0, 255), 2)

    encode_sample = face_recognition.face_encodings(sample_image)[0]

    encode_test = face_recognition.face_encodings(test_image)[0]

    result = face_recognition.compare_faces([encode_sample], encode_test)
    distance = face_recognition.face_distance([encode_sample], encode_test)
    cv2.putText(test_image, f"{result} {round(distance[0], 2)}",
                (50, 50), cv2.FONT_HERSHEY_COMPLEX,
                1, (0, 0, 255), 2)

    cv2.imshow('Test', test_image)
    cv2.waitKey(0)

    return result
