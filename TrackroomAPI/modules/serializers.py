
import source
from source.utils import find_file_type

from rest_framework import serializers


from .models import Module, ContentMaterial, ContentMediaType


class ModuleSerializer(serializers.ModelSerializer):
    content_material = serializers.ListField(write_only=True)

    class Meta:
        model = Module
        fields = ['pk', 'classroom', 'title', 'description', 'date_created', 'content_material']
        read_only_fields = ['pk', 'classroom', 'date_created']

    def to_representation(self, instance):
        representation = super(ModuleSerializer, self).to_representation(instance)
        representation['date_created'] = instance.date_created.strftime('%d-%m-%Y')
        representation['post_type'] = "Module"
        return representation

    def validate_title(self, title):
        classroom = self.context['classroom']
        if Module.ModuleObject.filter(classroom=classroom, title=title).exists():
            raise serializers.ValidationError("An module with this title already exist")
        return title

    def validate_content_material(self, content_material):
        files = content_material.pop(0)
        content_material.clear()
        for file in files:
            type = find_file_type(file)
            content_material.append({'file': file, 'file_type': type})
        return content_material

    def create(self, validated_data):
        classroom = self.context['classroom']
        module = Module.ModuleObject.create(
                        classroom=classroom,
                        title=validated_data['title'],
                        description=validated_data['description'])
        for file in validated_data['content_material']:
            content_material = ContentMaterial.ContentMaterialObject.create(
                module=module,
                file=file['file'],
                file_type=ContentMediaType.objects.get(pk=file['file_type']))
        return module


class ContentMaterialSerializer(serializers.ModelSerializer):

    class Meta:
        model = ContentMaterial
        fields = ['file', 'file_type']


