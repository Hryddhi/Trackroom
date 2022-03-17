from django.contrib import admin

# Register your models here.
from .models import  AuthProvider, Account, Profile


@admin.register(AuthProvider)
class AuthProviderAdmin(admin.ModelAdmin):
    list_display = ['auth_provider']


@admin.register(Account)
class AccountAdmin(admin.ModelAdmin):
    list_display = ['email', 'auth_provider', 'date_joined']
    search_fields = ['email']
    readonly_fields = ['id', 'email', 'date_joined', 'last_login']


@admin.register(Profile)
class ProfileAdmin(admin.ModelAdmin):
    list_display = ['account', 'username', 'profile_image','bio']
    search_fields = ['account']
    readonly_fields = ['account']


