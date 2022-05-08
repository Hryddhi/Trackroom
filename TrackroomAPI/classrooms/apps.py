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

        def get_class_category(i):
            if i == 26:
                class_category = 'Web Development'
            elif i in [3, 13, 16, 17, 22, 25]:
                class_category = 'Machine Learning'
            elif i in [4, 8, 10, 24]:
                class_category = 'Calculus'
            elif i == 19:
                class_category = 'Quantum Physics'
            elif i in [18, 20, 21]:
                class_category = 'Others'
            else:
                class_category = 'Cooking'
            return class_category

        def get_class_rating(i):
            if i == 26:
                rating = 3.6
            elif i == 19:
                rating = 3.9
            elif i == 8:
                rating = 4.2
            elif i in [15, 16]:
                rating = 4.3
            else:
                rating = 0.0
            return rating

        def create_classroom(sender, **kwargs):
            account = Account.objects.get(email='test_user2@gmail.com')

            title = 'Test Classroom 1'
            if not Classroom.ClassroomObject.filter(title=title).exists():
                Classroom.ClassroomObject.create(
                    creator=account,
                    title=title,
                    description='This is the description for ' + title,
                    class_type=ClassType.objects.get(pk='Public'),
                    class_category=ClassCategory.objects.get(pk='Web Development')
                )

            title = 'Test Classroom 2'
            if not Classroom.ClassroomObject.filter(title=title).exists():
                Classroom.ClassroomObject.create(
                    creator=Account.objects.get(email='test_user1@gmail.com'),
                    title=title,
                    description='This is the description for ' + title,
                    class_type=ClassType.objects.get(pk='Public'),
                    class_category=ClassCategory.objects.get(pk='Cooking')
                )

            i = 3
            while i < 27:
                title = f'Test Classroom {i}'
                if i in [3, 13, 14]:
                    class_type = ClassType.objects.get(pk='Private')
                else:
                    class_type = ClassType.objects.get(pk='Public')

                if not Classroom.ClassroomObject.filter(title=title).exists():
                    Classroom.ClassroomObject.create(
                        creator=account,
                        title=title,
                        description='This is the description for ' + title,
                        class_type=class_type,
                        class_category=ClassCategory.objects.get(pk=get_class_category(i)),
                        ratings=get_class_rating(i)
                )
                i = i + 1

        def join_classroom(sender, **kwargs):
            account = Account.objects.get(email='test_user1@gmail.com')
            classroom = Classroom.ClassroomObject.get(title='Test Classroom 1')
            if not classroom.has_this_subscriber(account):
                Enrollment.EnrollmentObject.create(subscriber=account, classroom=classroom)

            account_2 = Account.objects.get(email='test_user2@gmail.com')
            classroom = Classroom.ClassroomObject.get(title='Test Classroom 2')
            if not classroom.has_this_subscriber(account_2):
                Enrollment.EnrollmentObject.create(subscriber=account, classroom=classroom)

            i = 3
            while i < 27:
                title = f'Test Classroom {i}'
                classroom = Classroom.ClassroomObject.get(title=title)
                if not classroom.has_this_subscriber(account):
                    en = Enrollment.EnrollmentObject.create(subscriber=account, classroom=classroom)
                    if i in [1, 8, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26]:
                        en.deactivate()
                i = i + 1


        post_migrate.connect(create_class_type, sender=self)
        post_migrate.connect(create_class_category, sender=self)
        post_migrate.connect(create_classroom, sender=self)
        post_migrate.connect(join_classroom, sender=self)

