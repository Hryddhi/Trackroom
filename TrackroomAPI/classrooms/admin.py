from django.contrib import admin
from .models import Classroom, Enrollment

@admin.register(Classroom)
class ClassroomAdmin(admin.ModelAdmin):
    list_display = [ 'id', 'title', 'teacher', 'date_created']
    search_fields = ['title', 'teacher']
    readonly_fields = ['id']

@admin.register(Enrollment)
class EnrollmentAdmin(admin.ModelAdmin):
    list_display = ['id', 'classroom', 'student', 'date_joined']
    search_fields = ['classroom', 'student']
    readonly_fields = ['id']
