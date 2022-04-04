import smtplib

import source
from source.utils import get_object_or_404
from source.base import CreateRetrieveUpdateViewSet, ListViewSet
from source.exceptions import EmailNotSentException

from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework.decorators import action
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet



from .serializers import (
    ClassroomSerializer, CreateClassroomSerializer,
    JoinPrivateClassroomSerializer, JoinPublicClassroomSerializer,
    RateClassroomSerializer,
    InviteSubscriberSerializer, send_invitation)

from .models import Classroom, ClassType
from .permissions import ClassroomViewPermission

from modules.models import Module
from modules.serializers import ModuleSerializer
from modules.permissions import ModuleViewPermission


class ClassroomViewSet(CreateRetrieveUpdateViewSet):
    permission_classes = [IsAuthenticated, ClassroomViewPermission, ]

    def get_queryset(self):
        qs = Classroom.ClassroomObject.all()
        return qs.filter(class_type=ClassType.PUBLIC) if self.action == 'search' else qs

    def get_object(self):
        queryset = self.get_queryset()
        pk = self.kwargs.get('pk')
        if pk.isnumeric():
            obj = get_object_or_404(queryset, pk=pk)
        else:
            obj = get_object_or_404(queryset, title=pk)

        if self.action in ['rate', 'leave']:
            obj = get_object_or_404(obj.subscribers, subscriber=self.request.user)

        self.check_object_permissions(self.request, obj)

        return obj

    def get_serializer_class(self):
        if self.action == 'create' and "code" in self.request.data:
            return JoinPrivateClassroomSerializer
        if self.action == 'create':
            return CreateClassroomSerializer
        elif self.action == 'join':
            return JoinPublicClassroomSerializer
        elif self.action == 'rate':
            return RateClassroomSerializer
        elif self.action == 'invite':
            return InviteSubscriberSerializer
        return ClassroomSerializer

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data, context={'account': request.user})
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=HTTP_201_CREATED)

    @action(methods=['get'], detail=False, url_path='search')
    def search(self, request, pk=None):
        queryset = self.get_queryset()
        if 'title' in request.GET.keys():
            queryset = queryset.filter(title__icontains=request.GET.get('title'))

        serializer = self.get_serializer(queryset, many=True)
        return Response(data=serializer.data, status=HTTP_200_OK)

    @action(methods=['post'], detail=True, url_path='join', permission_classes=[IsAuthenticated])
    def join(self, request, pk=None):
        classroom = self.get_object()
        data = {'classroom': classroom,
                'subscriber': request.user}
        serializer = self.get_serializer(data={}, context=data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(HTTP_201_CREATED)

    @action(methods=['post'], detail=True, url_path='invite')
    def invite(self, request, pk=None):
        classroom = self.get_object()
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        try:
            send_invitation(classroom, serializer.validated_data['subscriber'])
        except smtplib.SMTPException:
            raise EmailNotSentException("boop-boop!")   #Todo
        return Response(status=HTTP_202_ACCEPTED)

    @action(methods=['post'], detail=True, url_path='rate')
    def rate(self, request, pk=None):
        enrollment = self.get_object()
        serializer = self.get_serializer(enrollment, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=HTTP_202_ACCEPTED)

    @action(methods=['post'], detail=True, url_path='leave')
    def leave(self, request, pk=None):
        enrollment = self.get_object()
        enrollment.deactivate()
        return Response(status=HTTP_202_ACCEPTED)

    @action(methods=['post'], detail=True, url_path='create-module')
    def create_module(self, request, pk=None):
        data = request.data.copy()
        if request.FILES.get('content_material') is not None:
            data['content_material'] = request.FILES.pop('content_material')
        serializer = self.get_serializer(data=data, context={'classroom': self.get_object()})
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=HTTP_200_OK)

    @action(methods=['post'], detail=True, url_path='create-quiz')
    def create_quiz(self, request, pk=None):
        pass


class AccountWiseClassroomViewset(GenericViewSet):
    serializer_class = ClassroomSerializer
    permission_classes = [IsAuthenticated,]

    def get_queryset(self):
        account = self.request.user
        if self.action == 'created_classroom_list':
            return Classroom.get_created_classroom_of(account)
        elif self.action == 'joined_public_classroom_list':
            return Classroom.get_joined_classroom_of(account).filter(class_type=ClassType.PUBLIC)
        elif self.action == 'joined_private_classroom_list':
            return Classroom.get_joined_classroom_of(account).filter(class_type=ClassType.PRIVATE)

    @action(methods=['get'], detail=False, url_path='created-classroom-list')
    def created_classroom_list(self, request):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(data=serializer.data,
                        status=HTTP_200_OK)

    @action(methods=['get'], detail=False, url_path='joined-public-classroom-list')
    def joined_public_classroom_list(self, request):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(data=serializer.data,
                        status=HTTP_200_OK)

    @action(methods=['get'], detail=False, url_path='joined-private-classroom-list')
    def joined_private_classroom_list(self, request):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(data=serializer.data,
                        status=HTTP_200_OK)

    @action(methods=['get'], detail=False, url_path='notification-list')
    def notification_list(self, request):
        data = [
            {
                "classroom": "Test Classroom 1",
                "message": "A new content has been posted",
                "date": "17-03-22",
            },
            {
                "classroom": "Test Classroom 1",
                "message": "A new content has been posted",
                "date": "21-03-22",
            },
        ]
        return Response(data=data,
                        status=HTTP_200_OK)


class ClassroomTimelineViewset(ListViewSet):

    permission_classes = [IsAuthenticated, ModuleViewPermission]

    @property
    def classroom(self):
        return get_object_or_404(Classroom.ClassroomObject.all(),
                                 pk=self.kwargs.get('classroom_pk'))

    def get_queryset(self):
        return Module.get_created_module_from(self.classroom.pk).order_by('-date_created')

    def get_serializer_class(self):
        return ModuleSerializer

