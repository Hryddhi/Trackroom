from django.contrib import admin
from .models import Quiz


@admin.register(Quiz)
class QuizAdmin(admin.ModelAdmin):
    list_display = ['pk', 'classroom', 'title', 'date_created']
    search_fields = ['classroom', ]
    readonly_fields = ['pk']

