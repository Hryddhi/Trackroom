from django.shortcuts import render
from django.utils import timezone

from rest_framework import status
from rest_framework.views import APIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response

# class TestView(APIView):
#     permission_classes = [AllowAny]
#
#     def get(self, request, *args, **kwargs):
#         date = timezone.now().strftime('%d-%m-%Y')
#         print(date)
#         return Response(status=status.HTTP_200_OK)



