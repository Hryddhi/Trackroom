from django.conf import settings
from django.core.files import File

from rest_framework import serializers

from accounts.models import Profile
from .models import Comment


class CommentSerializer(serializers.ModelSerializer):
    creator = serializers.HiddenField(default=None)
    module = serializers.HiddenField(default=None, write_only=True)

    class Meta:
        model = Comment
        fields = ['pk', 'creator', 'module', 'comment', 'date_created']
        read_only_field = ['pk', 'date_created']

    def to_representation(self, instance):
        representation = super(CommentSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        representation['creator'] = f"{instance.creator.profile.username}({instance.creator.email})"
        representation['creator_image'] = "http://20.212.216.183" + instance.creator.profile.profile_image.url
        return representation
