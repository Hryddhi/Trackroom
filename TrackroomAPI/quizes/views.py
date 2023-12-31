import source
from source.utils import get_object_or_404
from source.base import RetrieveUpdateViewSet


from rest_framework.decorators import action
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework.parsers import JSONParser, MultiPartParser, FormParser
from rest_framework.status import HTTP_200_OK, HTTP_201_CREATED, HTTP_202_ACCEPTED
from rest_framework.exceptions import ValidationError
from rest_framework.response import Response
from rest_framework.viewsets import GenericViewSet

from .models import Quiz, Question, AssignedQuiz
from .serializers import (
    QuizSerializer, QuestionSerializer,
    QuizStatSerializer, QuizCreatorStatSerializer,
    QuizSubmitSerializer,
    FacialRecognitionSerializer)


class QuizViewSet(RetrieveUpdateViewSet):
    permission_classes = [AllowAny]

    @property
    def quiz(self):
        obj = get_object_or_404(Quiz.QuizObject.all(),
                                pk=self.kwargs.get('pk'))
        self.check_object_permissions(self.request, obj)
        return obj

    @property
    def account(self):
        return self.request.user

    def get_serializer_class(self):
        if self.action == 'question':
            return QuestionSerializer
        elif self.action == 'submit':
            return QuizSubmitSerializer
        return QuizSerializer

    def get_queryset(self):
        if self.action == 'question':
            return Question.QuestionObject.filter(quiz=self.quiz)
        elif self.action in ['quiz_stats', 'submit']:
            return AssignedQuiz.AssignedQuizObject.filter(quiz=self.quiz)

    def get_object(self):
        obj = self.quiz
        if self.action in ['quiz_stats', 'submit']:
            obj = get_object_or_404(self.get_queryset(),
                                    subscriber=self.account)
        return obj

    @action(methods=['get'], detail=True, url_path='question')
    def question(self, request, pk=None):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response(data=serializer.data,
                        status=HTTP_200_OK)

    @action(methods=['get'], detail=True, url_path='quiz-stats')
    def quiz_stats(self, request, pk=None):
        queryset = self.get_queryset()
        if self.quiz.classroom.has_this_subscriber(self.account):
            serializer = QuizStatSerializer(self.get_object())
        else:
            serializer = QuizCreatorStatSerializer(queryset, many=True)
        return Response(data=serializer.data,
                        status=HTTP_200_OK)

    @action(methods=['post'], detail=True, url_path='submit')
    def submit(self, request, pk=None):
        assigned_quiz = self.get_object()
        if assigned_quiz.has_attended:
            raise ValidationError("Already attended")
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save(attended_quiz=assigned_quiz)
        return Response(status=HTTP_202_ACCEPTED)


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


