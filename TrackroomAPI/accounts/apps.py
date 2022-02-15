from django.apps import AppConfig
from django.db.models.signals import post_migrate


class AccountsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'accounts'

    def ready(self):
        from .models import Account, AuthProvider

        def create_auth_provider(sender, **kwargs):
            for auth_provider in ['Email', 'Google']:
                if not AuthProvider.objects.filter(auth_provider=auth_provider).exists():
                    AuthProvider.objects.create(auth_provider=auth_provider)

        def create_superusers(sender, **kwargs):
            if not Account.objects.filter(email='hani@gmail.com').exists():
                Account.objects.create_superuser(email='hani@gmail.com',
                                                 username='hani',
                                                 password='project3')
            if not Account.objects.filter(email='rifat@gmail.com').exists():
                Account.objects.create_superuser(email='rifat@gmail.com',
                                                 username='rifat',
                                                 password='rifat')
            if not Account.objects.filter(email='hryddhi@gmail.com').exists():
                Account.objects.create_superuser(email='hryddhi@gmail.com',
                                                 username='hryddhi',
                                                 password='hryddhi')

        post_migrate.connect(create_auth_provider, sender=self)
        post_migrate.connect(create_superusers, sender=self)