import source
from source.utils import image_comparator

from rest_framework import serializers

from .models import Quiz, Question, Option


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
    question = serializers.ListField()

    class Meta:
        model = Quiz
        fields = ['pk', 'classroom', 'title', 'question', 'start_time', 'end_time', 'description', 'date_created']

    def validate_question(self, question_set):
        data = []
        for Q_element in question_set:
            option_set = Q_element['option']
            options = [{'option': option, 'is_correct': False} for option in option_set]
            options[Q_element['correct_answer'] - 1]['is_correct'] = True
            data.append({
                'question': Q_element['question'],
                'options': options
            })
        return data

    def create(self, validated_data):
        quiz = super(CreateQuizSerializer, self).create(validated_data)
        for Q_element in validated_data['question']:
            question = Question.QuestionObject.create(
                quiz=quiz,
                question=Q_element['question']
            )
            for option in Q_element['options']:
                Option.OptionObject.create(
                    question=question,
                    option=option['option'],
                    is_correct=option['is_correct']
                )
        return quiz


class FacialRecognitionSerializer(serializers.Serializer):
    image = serializers.ImageField()

    def validate(self, data):
        data['result'] = image_comparator("\\media\\Profile_image\\4.webp", data['image'])
        return data
