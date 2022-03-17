import source
from source.utils import image_validator

from django.conf import settings
from django.contrib.auth import authenticate
from rest_framework import serializers
from rest_framework.exceptions import AuthenticationFailed

from .models import AuthProvider, Account, Profile
from .google import Google


class RegistrationSerializer(serializers.ModelSerializer):
    password2 = serializers.CharField(style={'input_type': 'password'}, write_only=True)

    class Meta:
        model = Account
        fields = ['email', 'username', 'password', 'password2']
        extra_kwargs = {
            'password': {
                'write_only': True,
                'style': {'input_type': 'password'}
            }
        }

    def validate(self, data):
        password = data['password']
        password2 = data['password2']

        if password != password2:
            raise serializers.ValidationError({'password': 'Passwords must match', 'password2': 'Passwords must match'})
        return data

    def save(self):
        account = Account.objects.create_account(email=self.validated_data['email'],
                                                 username=self.validated_data['username'],
                                                 password=self.validated_data['password'])
        return account


class LoginSerializer(serializers.Serializer):

    email = serializers.EmailField(
        max_length=100,
        style={'placeholder': 'Email', 'autofocus': True}
    )
    password = serializers.CharField(
        max_length=100,
        style={'input_type': 'password', 'placeholder': 'Password'}
    )

    def validate(self, data):
        user = authenticate(**data)
        if user and user.is_active:
            return user
        raise AuthenticationFailed


class PasswordSerializer(serializers.Serializer):
    old_password = serializers.CharField(style={'input_type': 'password'}, write_only=True)
    new_password = serializers.CharField(style={'input_type': 'password'}, write_only=True)
    new_password2 = serializers.CharField(style={'input_type': 'password'}, write_only=True)

    def validate(self, data):
        data = super(PasswordSerializer, self).validate(data)

        if not self.context.get('account').check_password(data['old_password']):
            raise serializers.ValidationError({"old_password": "Wrong Password"})
        elif data['new_password'] != data['new_password2']:
            raise serializers.ValidationError({"new_password": "Passwords don't match", "new_password2": "Passwords don't match"})
        return data


class ProfileSerializer(serializers.ModelSerializer):

    class Meta:
        model = Profile
        fields = ['pk', 'username', 'profile_image', 'bio']
        read_only_fields = ['pk']

    def to_representation(self, instance):
        old_representation = super(ProfileSerializer, self).to_representation(instance)
        representation = {'pk': old_representation['pk'],
                          "email": instance.account.email}
        for x in old_representation:
            representation[x] = old_representation[x]
        return representation

    def validate_profile_image(self, data):
        if data is not None:
            image_validator(data)
            return data
        return None


# def validate_profile_image(self, data):
#     if data is not None and len(data) > 0:
#         base64_image_validator(data)
#         return data
#     return None


    def validate(self, data):
        data = super(ProfileSerializer, self).validate(data)

        # debugging #Todo
        print("In the account update validation:")
        print(data)

        return data


class GoogleAccountSerializer(serializers.Serializer):
    auth_token = serializers.CharField()

    def validate(self, data):
        auth_token = data['auth_token']
        user_data = Google.validate(auth_token)
        try:
            user_data['sub']
        except:
            raise serializers.ValidationError(
                'The token is invalid or expired. Please login again.'
            )

        print(user_data['aud'])
        if user_data['aud'] not in settings.GOOGLE_CLIENT_ID:#Todo
        # if user_data['aud'] != '407408718192.apps.googleusercontent.com':
            raise AuthenticationFailed('oops, who are you?')

        # debugging #Todo
        print("Data from Google:")
        print(user_data)

        data = {}
        data['email'] = user_data['email']
        try:
            data['username'] = user_data['name']
        except KeyError:
            data['username'] = ""   # Todo

        return data



def register_social_user(email, username, auth_provider):

    filtered_user = Account.objects.filter(email=email)

    if filtered_user.exists():

        # debugging #Todo
        print(settings.SOCIAL_SECRET[auth_provider])
        print(filtered_user[0].auth_provider.pk)

        if filtered_user[0].has_auth_provider(auth_provider):

            exisiting_account = authenticate(
                email=email, password=settings.SOCIAL_SECRET[auth_provider])
            if exisiting_account:
                return exisiting_account
            raise serializers.ValidationError("Authentication Failed")

        else:
            raise AuthenticationFailed(
                detail=f"Please continue your login using your {filtered_user[0].auth_provider}")

    else:
        account = Account.objects.create_account(email=email, username=username,
                                                 password=settings.SOCIAL_SECRET[auth_provider])
        # user.is_verified = True
        account.auth_provider = AuthProvider.objects.get(pk=auth_provider)
        account.save()

        new_account = authenticate(
            email=email, password=settings.SOCIAL_SECRET[auth_provider])
        return new_account