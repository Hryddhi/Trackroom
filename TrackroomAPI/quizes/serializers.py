import source
from source.utils import image_comparator

from rest_framework import serializers

from .models import Quiz, Question, Option, AssignedQuiz, Answer


class ListQuizSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz
        fields = ['pk', 'title', 'description', 'date_created']

    def to_representation(self, instance):
        representation = super(ListQuizSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        representation['post_type'] = "Quiz"
        representation['creator_image'] = instance.classroom.creator.profile.profile_image.url
        return representation


class QuizSerializer(serializers.ModelSerializer):
    questions = serializers.ListField(write_only=True)

    class Meta:
        model = Quiz
        fields = ['pk', 'title', 'questions', 'start_time', 'end_time',
                  'description', 'date_created']
        read_only_fields = ['pk', 'date_created']

    def to_representation(self, instance):
        representation = super(QuizSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        return representation

    def validate_title(self, title):
        classroom = self.context['classroom']
        if Quiz.QuizObject.filter(classroom=classroom, title=title).exists():
            raise serializers.ValidationError("A quiz with this title already exist")
        return title

    def validate_questions(self, question_set):
        data = []
        for Q_element in question_set:
            option_set = Q_element['options']
            options = [{'option': option, 'is_correct': False} for option in option_set]
            options[Q_element['correct_option'] - 1]['is_correct'] = True
            data.append({
                'question': Q_element['question'],
                'options': options
            })
        return data

    def create(self, validated_data):
        classroom = self.context['classroom']
        quiz = Quiz.QuizObject.create(
                classroom=classroom,
                title=validated_data['title'],
                description=validated_data['description'],
                start_time=validated_data['start_time'],
                end_time=validated_data['end_time'])

        for Q_element in validated_data['questions']:
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
        AssignedQuiz.assign_this_quiz_to_respective_subscribers(quiz)
        return quiz


class QuestionSerializer(serializers.ModelSerializer):
    options = serializers.ListField()

    class Meta:
        model = Question
        fields = ['pk', 'question', 'options']
        read_only_fields = ['pk', 'question', 'options']


class QuizStatSerializer(serializers.ModelSerializer):

    class Meta:
        model = AssignedQuiz
        fields = ['has_attended', 'grade']
        read_only_fields = ['has_attended', 'grade']


class QuizCreatorStatSerializer(serializers.ModelSerializer):

    class Meta:
        model = AssignedQuiz
        fields = ['subscriber', 'has_attended', 'grade']
        read_only_fields = ['subscriber', 'has_attended', 'grade']

    def to_representation(self, instance):
        representation = super(QuizCreatorStatSerializer, self).to_representation(instance)
        representation['subscriber'] = f"{instance.subscriber.profile.username}({instance.subscriber.email})"
        return representation


class QuizSubmitSerializer(serializers.Serializer):
    attended_quiz = serializers.HiddenField(default=None)
    answers = serializers.ListField()

    def validate_answers(self, answer_set):
        data = []
        for answer in answer_set:
            question = Question.QuestionObject.get(pk=answer['pk'])
            selected_option = Option.OptionObject.get(question=question, relative_index=(answer['selected_option']))
            data.append({
                'question': question,
                'selected_option': selected_option
            })
        return data

    def create(self, validated_data):
        attended_quiz = validated_data['attended_quiz']
        for answer in validated_data['answers']:
            Answer.AnswerObject.create(
                attended_quiz=attended_quiz,
                question=answer['question'],
                selected_option=answer['selected_option'])
        attended_quiz.auto_grade()
        return attended_quiz



class FacialRecognitionSerializer(serializers.Serializer):
    image = serializers.ImageField()

    def validate(self, data):
        data['result'] = image_comparator("\\media\\Profile_image\\4.webp", data['image'])
        return data
