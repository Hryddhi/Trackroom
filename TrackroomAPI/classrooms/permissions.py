from rest_framework.permissions import BasePermission, SAFE_METHODS
from .models import Classroom


class ClassroomViewPermission(BasePermission):
    message = {"detail": "User does not have access to this classroom"}

    def has_object_permission(self, request, view, obj):
        if request.method in SAFE_METHODS:
            return obj.has_this_member(request.user) if isinstance(obj, Classroom) else obj.classroom.has_this_member(request.user)
        return obj.has_this_creator(request.user) if isinstance(obj, Classroom) else obj.classroom.has_this_subscriber(request.user)
