from rest_framework import serializers

from .models import Notification


class NotificationSerializer(serializers.ModelSerializer):
    classroom = serializers.SerializerMethodField()

    class Meta:
        model = Notification
        fields = ['classroom', 'message', 'date_created']
        read_only_fields = ['classroom', 'message', 'date_created']

    def to_representation(self, instance):
        representation = super(NotificationSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        return representation

    def get_classroom(self, instance):
        return instance.related_classroom.title