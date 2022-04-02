from rest_framework.permissions import BasePermission, SAFE_METHODS


class ModuleViewPermission(BasePermission):
    message = {"detail": "User does not have access to this module"}

    def has_object_permission(self, request, view, obj):
        if request.method in SAFE_METHODS:
            return obj.classroom.has_this_member(request.user)
        return obj.classroom.has_this_creator(request.user)
