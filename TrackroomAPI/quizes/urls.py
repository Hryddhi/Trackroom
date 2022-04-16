from django.urls import path, include
from rest_framework.routers import SimpleRouter

from .views import FacialRecognitionView

app_name = "quizes"

router = SimpleRouter()
router.register(r'', FacialRecognitionView, basename='face')

urlpatterns = [
    path('api/', include((router.urls, 'face'))),

]
