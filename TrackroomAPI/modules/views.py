from django.shortcuts import render
from django.utils import timezone

from source.utils import get_object_or_404
from source.base import CreateListRetrieveUpdateViewSet

from rest_framework import status
from rest_framework.decorators import action
from rest_framework.views import APIView  # TO_BE_CUT_OUT
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet

from classrooms.models import Classroom

from .models import Module
from .serializers import ModuleSerializer


class ClassWiseAssignmentViewset(CreateListRetrieveUpdateViewSet):

    permission_classes = [AllowAny]
    parser_classes = [MultiPartParser, FormParser]

    @property
    def classroom(self):
        return get_object_or_404(Classroom.ClassroomObject.all(),
                                 pk=self.kwargs.get('classroom_pk'))

    def get_queryset(self):
        return Module.get_created_module_from(self.classroom.pk)

    def get_serializer_class(self):
        return ModuleSerializer

    def get_object(self):
        pass

    def create(self, request, *args, **kwargs):
        data = request.data.copy()
        if request.FILES.get('content_material') is not None:
            data['content_material'] = request.FILES.pop('content_material')
        serializer = self.get_serializer(data=data, context={'classroom': self.classroom})
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(status=HTTP_200_OK)



# class TestView(APIView):
#     permission_classes = [AllowAny]
#
#     def get(self, request, *args, **kwargs):
#         date = timezone.now().strftime('%d-%m-%Y')
#         print(date)
#         return Response(status=status.HTTP_200_OK)





