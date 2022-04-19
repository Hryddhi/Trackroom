from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .views import TestView

from .views import CommentViewset

app_name = "modules"

router = SimpleRouter()
router.register(r'comment', CommentViewset, basename='comment')

urlpatterns = [
    path('api/module/<pk>/', include((router.urls, 'comment'))),
]
