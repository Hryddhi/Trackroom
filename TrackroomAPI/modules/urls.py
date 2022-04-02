from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .views import TestView

from .views import ModuleView

app_name = "modules"

router = SimpleRouter()

urlpatterns = [
    path('api/module/<pk>', ModuleView.as_view()),
    # path('api/test', TestView.as_view(), name="test"),

]