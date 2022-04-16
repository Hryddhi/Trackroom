import source
from source.utils import image_comparator

from rest_framework import serializers

from .models import Quiz


class ListQuizSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz
        fields = ['pk', 'title', 'description', 'date_created']

    def to_representation(self, instance):
        representation = super(ListQuizSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        representation['post_type'] = "Quiz"
        return representation


class CreateQuizSerializer(serializers.ModelSerializer):
    classroom = serializers.HiddenField(default=None, write_only=True)
    question = serializers.JSONField()

    class Meta:
        model = Quiz
        fields = ['pk', 'title', 'classroom', 'description', 'date_created']

    def validate_question(self, data):
        print(data)
        pass


class FacialRecognitionSerializer(serializers.Serializer):
    image = serializers.ImageField()

    def validate(self, data):
        data['result'] = image_comparator("\\media\\Profile_image\\4.webp", data['image'])
        return data
