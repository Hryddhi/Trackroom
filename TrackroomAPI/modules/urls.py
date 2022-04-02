from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .views import TestView

from .views import ModuleViewset

app_name = "modules"

router = SimpleRouter()
router.register(r'module', ModuleViewset, basename='module')

urlpatterns = [
    path('api/', include((router.urls, 'module'))),
    # path('api/test', TestView.as_view(), name="test"),

]