from django.db import models
from django.contrib.auth.models import (
    AbstractBaseUser, BaseUserManager, PermissionsMixin)
# from model_utils.managers import InheritanceManager

from PIL import Image


class AuthProvider(models.Model):
    auth_provider = models.CharField(
        max_length=30, unique=True, primary_key=True,
        blank=False, null=False)

    objects = models.Manager()



class AccountManager(BaseUserManager):

    def create_account(self, email, username, password):
        if email is None:
            raise TypeError('Users must have a email address')
        if username is None:
            raise TypeError('Users must have a username')
        if password is None:
            raise TypeError('Users must have a password')

        user = self.model(email=self.normalize_email(email),
                          username=username,
                          auth_provider=AuthProvider.objects.get(pk='Email'))
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, username, password=None):
        if password is None:
            raise TypeError('Password should not be none')

        user = self.create_account(email=email, username=username, password=password)
        user.is_superuser = True
        user.is_admin = True
        user.is_staff = True
        user.save()
        return user



def profile_image_file_location(instance):
    image = Image.open(instance.profile_image)
    file_path = f"Profile_image/{instance.pk}.{image.format}"
    return file_path

def default_profile_image():
    return "Profile_image/default.jpg"


class Account(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(verbose_name='email', max_length=100, unique=True)  # ,db_index=True)
    username = models.CharField(verbose_name='username', max_length=50)
    date_joined = models.DateTimeField(verbose_name='date joined', auto_now_add=True)
    last_login = models.DateTimeField(verbose_name='last login', auto_now=True)
    # is_verified = models.BooleanField(default=False)
    is_admin = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    is_superuser = models.BooleanField(default=False)

    is_first_login = models.BooleanField(verbose_name='first_login', default=True)

    auth_provider = models.ForeignKey(AuthProvider, on_delete=models.PROTECT)

    profile_image = models.ImageField(null=True, default=None)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username']

    objects = AccountManager()

    def __str__(self):
        return self.username

    def has_perm(self, perm, obj=None):
        return self.is_admin

    def has_module_perms(self, app_label):
        return True

    def has_auth_provider(self, authProvider):
        return Account.get_accounts_from_auth_provider(authProvider).filter(pk=self.pk).exists()

    @staticmethod
    def get_accounts_from_auth_provider(authProvider):
        authProvider = AuthProvider.objects.get(pk=authProvider)
        return Account.objects.filter(auth_provider=authProvider)

