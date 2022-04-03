from django.urls import path, include
from rest_framework.routers import SimpleRouter

# from .random import RandomViewSet

from .views import (RegistrationView, LoginView,
                    BlacklistTokenView, AccountViewSet,
                    GoogleSignInView,
                    AccessTokenValidation
                    )

from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView

app_name = "accounts"

router = SimpleRouter()
router.register(r'account', AccountViewSet, basename='account')
# router.register(r'random', RandomViewSet, basename='random')

urlpatterns = [
    path('api/token', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh', TokenRefreshView.as_view(), name='token_refresh'),
    path('api/', include((router.urls, 'account'))),
    path('api/register', RegistrationView.as_view(), name="api-register"),
    path('api/login', LoginView.as_view(), name="api-login"),
    path('api/GoogleSignIn', GoogleSignInView.as_view(), name="api-google_sign_in"),
    path('api/logout/blacklist', BlacklistTokenView.as_view(), name='blacklist'),
    path('api/test-token', AccessTokenValidation.as_view(), name='test'),

    # path('', include((router.urls, 'random'))),

    # path('', list_view, name = "list"),
    # path('', views.AccountDetailsView.as_view(), name = 'user-detail'),

]