from django.contrib import admin
from .models import Quiz, Question, Option, AssignedQuiz


@admin.register(Quiz)
class QuizAdmin(admin.ModelAdmin):
    list_display = ['pk', 'classroom', 'title', 'date_created']
    search_fields = ['classroom', ]
    readonly_fields = ['pk']


@admin.register(Question)
class QuestionAdmin(admin.ModelAdmin):
    list_display = ['pk', 'quiz', 'question', 'options', 'correct_option']
    search_fields = ['quiz']
    readonly_fields = ['pk']


@admin.register(Option)
class OptionAdmin(admin.ModelAdmin):
    list_display = ['pk', 'question', 'option', 'label']
    search_fields = ['question']
    readonly_fields = ['pk']


@admin.register(AssignedQuiz)
class AssignedQuizAdmin(admin.ModelAdmin):
    list_display = ['subscriber', 'quiz', 'has_attended', 'grade']
    search_fields = ['subscriber', 'quiz']
    readonly_fields = ['pk']