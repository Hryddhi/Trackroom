from django.apps import AppConfig
from django.db.models.signals import post_migrate


class ClassroomsConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'classrooms'

    def ready(self):
        from accounts.models import Account
        from .models import ClassType, ClassCategory, Classroom, Enrollment
        
        def create_class_type(sender, **kwargs):
            for class_type in ClassType.CLASS_TYPE_CHOICES:
                if not ClassType.objects.filter(class_type=class_type).exists():
                    ClassType.objects.create(class_type=class_type)
        
        def create_class_category(sender, **kwargs):
            for category_name in ClassCategory.CLASS_CATEGORY_CHOICES:
                if not ClassCategory.objects.filter(category_name=category_name).exists():
                    ClassCategory.objects.create(category_name=category_name)

        def create_classroom(sender, **kwargs):
            if not Classroom.ClassroomObject.filter(title='Test Classroom 1').exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user1@gmail.com'),
                    title='Test Classroom 1',
                    description='This is the description for Test Classroom 1',
                    class_type=ClassType.objects.get(pk='Public'),
                    class_category=ClassCategory.objects.get(pk='Calculus')
                )
            if not Classroom.ClassroomObject.filter(title='Test Classroom 2').exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user2@gmail.com'),
                    title='Test Classroom 2',
                    description='This is the description for Test Classroom 2',
                    class_type=ClassType.objects.get(pk='Public'),
                    class_category=ClassCategory.objects.get(pk='Cooking')
                )
            if not Classroom.ClassroomObject.filter(title='Test Classroom 3').exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user2@gmail.com'),
                    title='Test Classroom 3',
                    description='This is the description for Test Classroom 3',
                    class_type=ClassType.objects.get(pk='Private'),
                    class_category=ClassCategory.objects.get(pk='Cooking')
                )

        def join_classroom(sender, **kwargs):
            account = Account.objects.get(email='test_user1@gmail.com')
            classroom = Classroom.ClassroomObject.get(title='Test Classroom 2')
            if not classroom.has_this_subscriber(account):
                Enrollment.EnrollmentObject.create(subscriber=account, classroom=classroom)

        post_migrate.connect(create_class_type, sender=self)
        post_migrate.connect(create_class_category, sender=self)
        post_migrate.connect(create_classroom, sender=self)
        post_migrate.connect(join_classroom, sender=self)

