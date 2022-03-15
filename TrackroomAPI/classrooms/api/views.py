import smtplib

import source
from source.utils import get_object_or_404
from source.base import CreateListRetrieveUpdateViewSet
from source.exceptions import EmailNotSentException

from rest_framework import status
from rest_framework.decorators import action
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet


from .serializers import (
    ClassroomSerializer, JoinPrivateClassroomSerializer,
    JoinPublicClassroomSerializer,
    InviteStudentSerializer, send_invitation)

from ..models import Classroom
from ..permissions import ClassroomViewPermission


class ClassroomViewSet(CreateListRetrieveUpdateViewSet):
    permission_classes = [IsAuthenticated, ClassroomViewPermission, ]

    def get_queryset(self):
        return Classroom.get_joined_classroom_of(self.request.user)

    def get_object(self):
        queryset = self.get_queryset()
        pk = self.kwargs.get('pk')
        if pk.isnumeric():
            obj = get_object_or_404(queryset, pk=pk)
        else:
            obj = get_object_or_404(queryset, title=pk)

        self.check_object_permissions(self.request, obj)
        return obj

    def get_serializer_class(self):
        if self.action == 'create' and self.request.data.has_key('code'):
            return JoinPrivateClassroomSerializer
        elif self.action == 'invite_students':
            return InviteStudentSerializer
        elif self.action == 'join' :
            return JoinPublicClassroomSerializer
        return ClassroomSerializer

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data, context={'account': request.user})
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=status.HTTP_201_CREATED)

    @action(methods=['post'], detail=True, url_path='invite-students')
    def invite_students(self, request, pk=None):
        classroom = self.get_object()
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        try:
            send_invitation(classroom, serializer.validated_data['student'])
        except smtplib.SMTPException:
            raise EmailNotSentException("boop-boop!")   #Todo
        return Response(status=status.HTTP_202_ACCEPTED)

    @action(methods=['post'], detail=True, url_path='join')
    def join (self, request, pk=None):
        classroom = self.get_object()
        data = {'classroom': classroom,
                'subscriber': request.user}
        serializer = self.get_serializer(context=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=status.HTTP_201_CREATED)


class AccountWiseClassroomViewset(GenericViewSet):
    serializer_class = ClassroomSerializer
    permission_classes = [IsAuthenticated,]

    def get_queryset(self):
        account = self.request.user
        if self.action == 'created_classroom_list':
            return Classroom.get_created_classroom_of(account)
        elif self.action == 'joined_public_classroom_list':
            return Classroom.get_joined_public_classroom_of(account)
        elif self.action == 'joined_private_classroom_list':
            return Classroom.get_joined_private_classroom_of(account)

    @action(methods=['get'], detail=False, url_path='created-classroom-list')
    def created_classroom_list(self, request, pk=None):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)

    @action(methods=['get'], detail=False, url_path='joined-public-classroom-list')
    def joined_public_classroom_list(self, request, pk=None):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)

    @action(methods=['get'], detail=False, url_path='joined-private-classroom-list')
    def joined_private_classroom_list(self, request, pk=None):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(serializer.data)
