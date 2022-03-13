from django.apps import AppConfig
from django.db.models.signals import post_migrate


class ClassroomsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'classrooms'

    def ready(self):
        from accounts.models import Account
        from .models import ClassType, Category, Classroom
        
        def create_class_type(sender, **kwargs):
            for class_type in ['Public', 'Private']:
                if not ClassType.objects.filter(class_type=class_type).exists():
                    ClassType.objects.create(class_type=class_type)
        
        def create_class_category(sender, **kwargs):
            category_list = [
                'Calculus', 'Quantum Physics',
                'English Literature', 'Machine Learning',
                'Cooking', 'Web Development',
                'Others'
            ]
            for category_name in category_list:
                if not Category.objects.filter(category_name=category_name).exists():
                    ClassType.objects.create(category_name=category_name)

        def create_classroom(sender, **kwargs):
            if not Classroom.ClassroomObject.filter(title='Test Classroom 1').exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user1@gmail,com'),
                    title='Test Classroom 1',
                    description='This is the description for Test Classroom 1',
                    class_type=ClassType.objects.get('Public'),
                )
            if not Classroom.ClassroomObject.filter(title='Test Classroom 2').exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user2@gmail,com'),
                    title='Test Classroom 2',
                    description='This is the description for Test Classroom 2',
                    class_type=ClassType.objects.get('Public'),
                )
        
        post_migrate.connect(create_class_type, sender=self)
        post_migrate.connect(create_class_category, sender = self)
        post_migrate.connect(create_classroom, sender = self)

