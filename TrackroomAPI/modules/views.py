from django.shortcuts import render
from django.utils import timezone

from source.utils import get_object_or_404
from source.base import ListViewSet

from rest_framework import status
from rest_framework.decorators import action
from rest_framework.views import APIView  # TO_BE_CUT_OUT
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import JSONParser, MultiPartParser, FormParser
from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet

from classrooms.models import Classroom

from .models import Module, ContentMaterial
from .serializers import ContentMaterialSerializer
from .permissions import ModuleViewPermission

# class TestView(APIView):
#     permission_classes = [AllowAny]
#
#     def get(self, request, *args, **kwargs):
#         date = timezone.now().strftime('%d-%m-%Y')
#         print(date)
#         return Response(status=status.HTTP_200_OK)


class ModuleViewset(ListViewSet):

    permission_classes = [IsAuthenticated, ModuleViewPermission]
    parser_classes = [MultiPartParser, FormParser]

    def get_queryset(self):
        pk = self.kwargs.get('module_pk')
        module = get_object_or_404(Module.ModuleObject.all(), pk=pk)
        self.check_object_permissions(self.request, module)
        return ContentMaterial.ContentMaterialObject.filter(module=module)

    serializer_class = ContentMaterialSerializer

