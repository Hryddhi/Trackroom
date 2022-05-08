from django.contrib import admin
from .models import Classroom, PrivateClassroom, Enrollment


@admin.register(Classroom)
class ClassroomAdmin(admin.ModelAdmin):
    list_display = ['id', 'title', 'creator', 'date_created', 'class_type', 'class_category', 'ratings']
    search_fields = ['title', 'creator']
    readonly_fields = ['id']


@admin.register(PrivateClassroom)
class PrivateClassroomAdmin(admin.ModelAdmin):
    list_display = ['classroom', 'code']
    readonly_fields = ['classroom']


@admin.register(Enrollment)
class EnrollmentAdmin(admin.ModelAdmin):
    list_display = ['id', 'classroom', 'subscriber', 'date_joined', 'is_active']
    search_fields = ['classroom', 'subscriber']
    readonly_fields = ['id']
