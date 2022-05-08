from django.contrib import admin
from .models import Comment


@admin.register(Comment)
class CommentAdmin(admin.ModelAdmin):
    list_display = ['pk', 'creator', 'module', 'comment']
    search_fields = ['module']
    readonly_fields = ['pk']

