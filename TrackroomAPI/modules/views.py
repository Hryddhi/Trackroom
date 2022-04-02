from django.shortcuts import render
from django.utils import timezone

from source.utils import get_object_or_404

from rest_framework import status
from rest_framework.decorators import action
from rest_framework.views import APIView  # TO_BE_CUT_OUT
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet
from rest_framework.generics import RetrieveUpdateAPIView

from classrooms.models import Classroom

from .models import Module
from .serializers import ContentMaterialSerializer

# class TestView(APIView):
#     permission_classes = [AllowAny]
#
#     def get(self, request, *args, **kwargs):
#         date = timezone.now().strftime('%d-%m-%Y')
#         print(date)
#         return Response(status=status.HTTP_200_OK)


class ModuleView(RetrieveUpdateAPIView):

    permission_classes = [AllowAny]
    parser_classes = [MultiPartParser, FormParser]

    queryset = Module.ModuleObject.all()
    serializer_class = ContentMaterialSerializer

