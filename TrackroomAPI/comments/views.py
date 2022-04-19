import source
from source.utils import get_object_or_404
from source.base import CreateListViewset

from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.exceptions import PermissionDenied
from rest_framework.status import HTTP_201_CREATED
from rest_framework.response import Response

from modules.models import Module

from .models import Comment
from .serializers import CommentSerializer


class CommentViewset(CreateListViewset):
    permission_classes = [AllowAny]

    @property
    def module(self):
        pk = self.kwargs.get('pk')
        return get_object_or_404(Module.ModuleObject.all(), pk=pk)

    def check_permissions(self, request):
        super(CommentViewset, self).check_permissions(request)
        if not self.module.classroom.has_this_member(request.user):
            raise PermissionDenied()

    def get_queryset(self):
        return Comment.CommentObject.filter(module=self.module)

    serializer_class = CommentSerializer

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save(creator=request.user, module=self.module)
        return Response(status=HTTP_201_CREATED)
