import source
from source.base import RetrieveUpdateViewSet
from source.utils import get_object_or_404, delete_previous_file

from rest_framework import status
from rest_framework.permissions import AllowAny, IsAuthenticated
from rest_framework.decorators import action, parser_classes
from rest_framework.response import Response
from rest_framework.parsers import MultiPartParser, FormParser, JSONParser

from rest_framework.generics import GenericAPIView
from rest_framework.views import APIView
from rest_framework import viewsets

from rest_framework_simplejwt.tokens import RefreshToken

from .serializers import (
    RegistrationSerializer, LoginSerializer,
    AccountSerializer, PasswordSerializer,
    GoogleAccountSerializer, register_social_user

)
from ..models import Account
from ..permissions import OwnProfile


class RegistrationView(GenericAPIView):
    permission_classes = [AllowAny]
    serializer_class = RegistrationSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        account = serializer.save()
        return Response(status=status.HTTP_201_CREATED)


class LoginView(GenericAPIView):
    permission_classes = [AllowAny]
    serializer_class = LoginSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)

        serializer.is_valid(raise_exception=True)
        account = serializer.validated_data
        refresh_token = get_refresh_tokens(account)
        return Response({"refresh": refresh_token['refresh'],
                         "access": refresh_token['access']},
                        status=status.HTTP_202_ACCEPTED)


class GoogleSignInView(GenericAPIView):
    permission_classes = [AllowAny]
    serializer_class = GoogleAccountSerializer

    def post(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        account = register_social_user(email=serializer.validated_data['email'],
                                       username=serializer.validated_data['username'],
                                       auth_provider='Google')

        refresh_token = get_refresh_tokens(account)
        return Response({"refresh": refresh_token['refresh'],
                         "access": refresh_token['access']},
                        status=status.HTTP_202_ACCEPTED)


class BlacklistTokenView(APIView):

    permission_classes = [AllowAny]

    def post(self, request):
        try:
            refresh_token = request.data["refresh_token"]
            token = RefreshToken(refresh_token)
            token.blacklist()
            return Response(status=status.HTTP_200_OK)
        except Exception as e:
            return Response({"error": e.args}, status=status.HTTP_400_BAD_REQUEST)


class AccountViewSet(RetrieveUpdateViewSet):

    permission_classes = [IsAuthenticated, OwnProfile]

    def get_serializer_class(self):
        if self.action == 'change_password':
            return PasswordSerializer
        else:
            return AccountSerializer

    queryset = Account.objects.all()

    def get_object(self):
        queryset = self.get_queryset()
        pk = self.kwargs.get('pk')
        if pk == 'u':
            pk = self.request.user.pk
        obj = get_object_or_404(queryset, pk=pk)

        self.check_object_permissions(self.request, obj)
        return obj

    @action(methods=['put'], detail=True, url_path='change-password')
    def change_password(self, request, pk=None):
        account = self.get_object()
        serializer = self.get_serializer(data=request.data, context={'account': account})
        serializer.is_valid(raise_exception=True)
        account.set_password(serializer.validated_data['new_password'])
        account.save()
        return Response(status=status.HTTP_202_ACCEPTED)

    def update(self, request, *args, **kwargs):
        partial = kwargs.pop('partial', False)
        account = self.get_object()
        serializer = self.get_serializer(account, data=request.data, partial=partial)
        serializer.is_valid(raise_exception=True)
        account = serializer.save()

        return Response(self.get_serializer(account).data,
                        status=status.HTTP_202_ACCEPTED)

    def partial_update(self, request, *args, **kwargs):
        kwargs['partial'] = True
        return self.update(request, *args, **kwargs)


def get_refresh_tokens(account):
    refresh = RefreshToken.for_user(account)
    return {
        'refresh': str(refresh),
        'access': str(refresh.access_token)
    }


class AccessTokenValidation(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        return Response(status=status.HTTP_200_OK)
