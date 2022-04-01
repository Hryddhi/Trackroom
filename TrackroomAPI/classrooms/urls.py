from django.urls import path, include
from rest_framework.routers import SimpleRouter
from .views import ClassroomViewSet, AccountWiseClassroomViewset

app_name = "classrooms"

router = SimpleRouter()
router.register(r'classroom', ClassroomViewSet, basename='classroom')
router.register(r'account/u', AccountWiseClassroomViewset, basename='account')

urlpatterns = [
    path('api/', include((router.urls, 'classroom'))),
    path('api/', include((router.urls, 'account'))),

]

# urlpatterns += router.urls