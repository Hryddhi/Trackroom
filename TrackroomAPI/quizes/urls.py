from django.urls import path, include
from rest_framework.routers import SimpleRouter

from .views import QuizViewSet, FacialRecognitionView

app_name = "quizes"

router = SimpleRouter()
router.register(r'quiz', QuizViewSet, basename='quiz')
router.register(r'', FacialRecognitionView, basename='face')

urlpatterns = [
    path('api/', include((router.urls, 'quiz'))),
    path('api/', include((router.urls, 'face'))),

]
