import source
from source.utils import get_object_or_404
from source.base import RetrieveUpdateViewSet


from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import JSONParser, MultiPartParser, FormParser


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


class ModuleViewset(RetrieveUpdateViewSet):

    permission_classes = [IsAuthenticated, ModuleViewPermission]
    parser_classes = [MultiPartParser, FormParser]

    queryset = Module.ModuleObject.all()

    def get_object(self):
        pk = self.kwargs.get('pk')
        module = get_object_or_404(self.get_queryset(), pk=pk)
        self.check_object_permissions(self.request, module)
        return ContentMaterial.ContentMaterialObject.get(module=module)

    serializer_class = ContentMaterialSerializer



