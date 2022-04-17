from django.contrib import admin
from .models import Notification


@admin.register(Notification)
class NotificationAdmin(admin.ModelAdmin):
    list_display = ['pk', 'related_classroom', 'related_model_type', 'message']
    search_fields = ['related_classroom', ]
    readonly_fields = ['pk']
