from rest_framework.permissions import BasePermission, SAFE_METHODS


class ClassroomViewPermission(BasePermission):
    message = {"detail": "User does not have access to this classroom"}

    def has_object_permission(self, request, view, obj):
        print("In permission class:")
        print(view)
        if request.method in SAFE_METHODS:
            return obj.has_this_member(request.user)
        return obj.has_this_teacher(request.user)
