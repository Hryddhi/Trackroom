from rest_framework import serializers


from .models import Module, ContentMaterial


class ModuleSerializer(serializers.ModelSerializer):
    content_material = serializers.FileField(required=True)

    class Meta:
        model = Module
        exclude = ['date_updated']
        read_only_fields = ['pk', 'classroom', 'date_created']

    def to_representation(self, instance):
        representation = super(ModuleSerializer, self).to_representation(instance)
        representation['classroom'] = instance.classroom.title
        return representation

    def validate_title(self, title):
        classroom = self.context['classroom']
        if Module.ModuleObject.filter(classroom=classroom, title=title).exists():
            raise serializers.ValidationError("An module with this title already exist")
        return title

    def validate_content_material(self, content_material):
        # Todo
        return content_material

    def create(self, validated_data):
        classroom = self.context['classroom']
        module = Module.ModuleObject.create(
                        classroom=classroom,
                        title=validated_data['title'],
                        description=validated_data['description'])
        content_material = ContentMaterial.ContentMaterialObject.create(
            module=module,
            file=validated_data['content_material']
        )
        return module