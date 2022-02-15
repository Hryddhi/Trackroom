from django.contrib.auth.models import Permission
from rest_framework import permissions
from rest_framework.permissions import BasePermission


# Object View Permissions


class OwnAccount(BasePermission):
    message = {"error": "User does not have access to this account"}

    def has_object_permission(self, request, view, obj):
        return obj == request.user


# Rest Framework Write Permissions
