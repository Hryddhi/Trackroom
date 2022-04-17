import source
from source.utils import get_object_or_404

from rest_framework.decorators import action
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import JSONParser, MultiPartParser, FormParser
from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet

from .serializers import FacialRecognitionSerializer

# class QuizViewSet()



class FacialRecognitionView(GenericViewSet):
    permission_classes = [AllowAny]
    parser_classes = [MultiPartParser, FormParser]

    serializer_class = FacialRecognitionSerializer

    @action(methods=['post'], detail=False, url_path='face')
    def face(self, request):
        # data = {"image": request.FILES.pop("image")}
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        print(serializer.validated_data)
        return Response(status=HTTP_200_OK)


