from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .views import TestView

from .views import ClassWiseAssignmentViewset

app_name = "modules"

router = SimpleRouter()
router.register(r'module', ClassWiseAssignmentViewset, basename='module')

urlpatterns = [
    path('api/classroom/<classroom_pk>/', include((router.urls, 'module'))),
    # path('api/test', TestView.as_view(), name="test"),

]