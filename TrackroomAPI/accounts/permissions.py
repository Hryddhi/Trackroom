from django.contrib.auth.models import Permission
from rest_framework import permissions
from rest_framework.permissions import BasePermission, SAFE_METHODS


# Object View Permissions


class OwnAccount(BasePermission):
    message = {"error": "User does not have access to this account"}

    def has_object_permission(self, request, view, obj):
        if request.method in SAFE_METHODS:
            return True
        return obj == request.user


# Rest Framework Write Permissions
