from django.urls import path, include
from rest_framework.routers import SimpleRouter
from .views import ClassroomViewSet, AccountWiseClassroomViewset, ClassroomTimelineViewset

app_name = "classrooms"

router = SimpleRouter()
router.register(r'classroom', ClassroomViewSet, basename='classroom')
router.register(r'account/u', AccountWiseClassroomViewset, basename='account')
router.register(r'timeline', ClassroomTimelineViewset, basename='timeline')

urlpatterns = [
    path('api/', include((router.urls, 'classroom'))),
    path('api/', include((router.urls, 'account'))),
    path('api/classroom/<pk>/', include((router.urls, 'timeline'))),

]

# urlpatterns += router.urls