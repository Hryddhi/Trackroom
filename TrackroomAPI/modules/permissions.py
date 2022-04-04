from rest_framework.permissions import BasePermission, SAFE_METHODS


class ModuleViewPermission(BasePermission):
    type = ""
    message = {"detail": f"User does not have {type}access to this classroom"}

    def has_object_permission(self, request, view, obj):
        if request.method in SAFE_METHODS:
            return obj.classroom.has_this_member(request.user)
        self.type = "write "
        return obj.classroom.has_this_creator(request.user)
