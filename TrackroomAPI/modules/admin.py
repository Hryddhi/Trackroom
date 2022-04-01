from django.contrib import admin
from .models import Module, ContentMaterial


@admin.register(Module)
class ModuleAdmin(admin.ModelAdmin):
    list_display = ['pk', 'classroom', 'title', 'description', 'date_created', 'content_material']
    search_fields = ['classroom']
    readonly_fields = ['pk', 'classroom']


@admin.register(ContentMaterial)
class ContentMaterialAdmin(admin.ModelAdmin):
    list_display = ['module', 'file', 'file_type']
    search_fields = ['module']
    readonly_fields = ['module']
