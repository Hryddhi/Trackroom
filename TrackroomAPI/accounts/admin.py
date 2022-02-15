from django.contrib import admin

# Register your models here.
from .models import Account, AuthProvider


@admin.register(Account)
class AccountAdmin(admin.ModelAdmin):
    list_display = ['email', 'username', 'auth_provider', 'date_joined']
    search_fields = ['email', 'username']
    readonly_fields = ['id', 'date_joined', 'last_login']


@admin.register(AuthProvider)
class AuthProviderAdmin(admin.ModelAdmin):
    list_display = ['auth_provider']


# admin.site.register(Account, AccountAdmin)
# admin.site.register(AuthProvider, AuthProviderAdmin)
