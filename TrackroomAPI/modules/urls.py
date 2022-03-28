from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .views import TestView

app_name = "modules"

router = SimpleRouter()

urlpatterns = [
    # path('api/', include((router.urls, 'module'))),
    # path('api/test', TestView.as_view(), name="test"),

]