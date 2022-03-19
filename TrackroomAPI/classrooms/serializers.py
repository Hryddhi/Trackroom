from django.utils.crypto import get_random_string
from django.core.mail import send_mail, get_connection

from rest_framework import serializers

from accounts.models import Account
from .models import ClassCategory, ClassType, Classroom, PrivateClassroom, Enrollment

class ClassroomSerializer(serializers.ModelSerializer):

    class Meta:
        model = Classroom
        fields = ['pk', 'creator', 'title', 'class_type', 'description', 'class_category']
        read_only_fields = ['pk', 'creator', 'class_type']

    def to_representation(self, instance):
        representation = super(ClassroomSerializer, self).to_representation(instance)
        representation['creator'] = instance.creator.profile.username
        representation['class_type'] = instance.class_type.pk
        representation['class_category'] = instance.class_category.pk
        representation['ratings'] = instance.ratings
        return representation


class CreateClassroomSerializer(serializers.ModelSerializer):

    class Meta:
        model = Classroom
        fields = ['creator', 'title', 'description', 'class_type', 'class_category']
        write_only_fields = ['creator', 'title', 'description', 'class_type', 'class_category']

    def create(self, validated_data):
        creator = self.context['account']
        classroom = Classroom.ClassroomObject.create(
                        creator=creator,
                        title=validated_data['title'],
                        description=validated_data['description'],
                        class_type=validated_data['class_type'],
                        class_category=validated_data['class_category']
                        )
        return classroom


def validate_enrollment(classroom, subscriber):
    if classroom.has_this_subscriber(subscriber):
        raise serializers.ValidationError({"User is already enrolled in this classroom"})
    data = {'subscriber': subscriber, 'classroom': classroom}
    return data


def create_enrollment(validated_data):
    subscriber = validated_data['subscriber']
    classroom = validated_data['classroom']
    enrollment = Enrollment.EnrollmentObject.create(subscriber=subscriber, classroom=classroom)
    return enrollment


class JoinPrivateClassroomSerializer(serializers.Serializer):
    code = serializers.CharField(required=True)

    def validate(self, data):
        data = super(JoinPrivateClassroomSerializer, self).validate(data)
        subscriber = self.context['account']
        code = data['code']
        if not PrivateClassroom.does_code_exist(code):
            raise serializers.ValidationError({"code": "Classroom does not exist"})

        private_classroom = PrivateClassroom.PrivateClassroomObject.get(code=code)
        classroom = private_classroom.classroom
        return validate_enrollment(classroom, subscriber)

    def create(self, validated_data):
        return create_enrollment(validated_data)


class JoinPublicClassroomSerializer(serializers.Serializer):

    def validate(self, data):
        data = self.context
        return validate_enrollment(data['classroom'], data['subscriber'])

    def create(self, validated_data):
        return create_enrollment(validated_data)

class RateClassroomSerializer(serializers.ModelSerializer):

    class Meta:
        model = Enrollment
        fields = ['rating']
        write_only_fields = ['rating']


class InviteSubscriberSerializer(serializers.Serializer):
    subscriber = serializers.ListField(child=serializers.EmailField(), allow_empty=False)

    def validate_subscriber(self, data):
        for x in data:
            if not Account.objects.filter(email=x).exists():
                message = f"{x} is not a user of Trackroom"
                raise serializers.ValidationError({'user': message})
        return data


def send_invitation(classroom, subscriber_list):
    connection = get_connection()
    for x in subscriber_list:
        subscriber = Account.objects.get(email=x)
        send_mail(
            subject="Trackroom Invitation",
            message=f"Hi, {subscriber.username}. You have been invited to join {classroom.creator}'s Classroom with the following code: \n{classroom.code}",
            from_email=None,
            recipient_list=[x],
            connection=connection,
            fail_silently=False
        )

