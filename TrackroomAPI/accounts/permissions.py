from django.contrib.auth.models import Permission
from rest_framework import permissions
from rest_framework.permissions import BasePermission, SAFE_METHODS

from .models import Profile


# Object View Permissions


class OwnProfile(BasePermission):
    message = {"error": "User does not have access to this account"}

    def has_object_permission(self, request, view, obj):
        if isinstance(obj, Profile):
            return True if request.method in SAFE_METHODS else obj.account == request.user
        return obj == request.user


# Rest Framework Write Permissions
