from django.urls import path, include
from rest_framework.routers import SimpleRouter
from .views import ClassroomViewSet

app_name = "classrooms"

router = SimpleRouter()
router.register(r'classroom', ClassroomViewSet, basename='classroom')

urlpatterns = [
    path('api/', include((router.urls, 'classroom'))),

]

# urlpatterns += router.urls