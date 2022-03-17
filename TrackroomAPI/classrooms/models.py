from django.utils import timezone
from django.utils.crypto import get_random_string
from django.db import models

from accounts.models import Account


def get_list_of_joined_classroom(account, **kwargs):
    e = Enrollment.EnrollmentObject.filter(subscriber=account)
    return [x.classroom.pk for x in e]


class ClassType (models.Model):
    PUBLIC = 'Public'
    PRIVATE = 'Private'

    CLASS_TYPE_CHOICES = [PUBLIC, PRIVATE]
    class_type = models.CharField(
        max_length=30, unique=True, primary_key=True,
        blank=False, null=False)
    objects = models.Manager()


class ClassCategory(models.Model):

    CLASS_CATEGORY_CHOICES = [
        'Calculus', 'Quantum Physics',
        'English Literature', 'Machine Learning',
        'Cooking', 'Web Development',
        'Others'
    ]
    category_name = models.CharField(
        max_length=30, unique=True, primary_key=True,
        blank=False, null=False)
    objects = models.Manager()


class ClassroomManager(models.Manager):

    def create(self, *args, **kwargs):
        classroom = super(ClassroomManager, self).create(*args, **kwargs)
        if classroom.class_type.pk == ClassType.PRIVATE:
            PrivateClassroom.PrivateClassroomObject.create(
                classroom=classroom).set_code()
        return classroom


class Classroom(models.Model):
    creator = models.ForeignKey(Account, on_delete=models.CASCADE, blank=False, null=False, editable=False)
    title = models.CharField(unique=True, max_length=100, blank=False, null=False)
    description = models.CharField(max_length=255, blank=True)
    class_type = models.ForeignKey(ClassType, on_delete=models.PROTECT, editable=False)
    class_category = models.ForeignKey(ClassCategory, on_delete=models.CASCADE, blank=False, null=False)
    date_created = models.DateTimeField(auto_now_add=True)

    @property
    def subscribers(self):
        return Enrollment.EnrollmentObject.filter(classroom=self).filter(is_active=True)

    @property
    def ratings(self):
        enrollment = Enrollment.EnrollmentObject.filter(classroom=self)
        rating_list = [e.rating for e in enrollment if e.rating is not None]
        return "No Ratings Yet" if len(rating_list) == 0 else sum(rating_list) / len(rating_list)

    ClassroomObject = ClassroomManager()

    def __str__(self):
        return self.title

    def has_this_creator(self, account):
        return self.creator == account

    def has_this_subscriber(self, account):
        entry = self.subscribers.filter(subscriber=account)
        return entry[0].is_active if entry.exists() else False

    def has_this_member(self, account):
        return self.has_this_creator(account) or self.has_this_subscriber(account)

    @staticmethod
    def get_created_classroom_of(account):
        return Classroom.ClassroomObject.filter(creator=account)

    @staticmethod
    def get_joined_classroom_of(account):
        return Classroom.ClassroomObject.filter(pk__in=get_list_of_joined_classroom(account))


class PrivateClassroom(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
    CURRENT_CODE_LENGTH = 6     # configurable for future increase in length
    code = models.CharField(unique=True, max_length=10)

    PrivateClassroomObject = models.Manager()

    def set_code(self):
        while self.does_code_exist(code := get_random_string(
                length=self.CURRENT_CODE_LENGTH)):
            self.code = code

    @staticmethod
    def does_code_exist(code):
        return PrivateClassroom.PrivateClassroomObject.filter(code=code).exists()


class EnrollmentManager(models.Manager):

    def create(self, *args, **kwargs):
        classroom = kwargs.get('classroom')
        subscriber = kwargs.get('subscriber')
        qs = self.get_queryset().filter(classroom=classroom, subscriber=subscriber)
        if not qs.exists():
            return super(EnrollmentManager, self).create(*args, **kwargs)
        return qs[0].activate()

    def get_queryset(self, *args, **kwargs):
        return super(EnrollmentManager, self).get_queryset(*args, **kwargs)


class Enrollment(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
    subscriber = models.ForeignKey(Account, on_delete=models.CASCADE, blank=False, null=False)
    date_joined = models.DateTimeField(auto_now_add=True)
    is_active = models.BooleanField(default=True)

    class Ratings(models.IntegerChoices):
        one = 1
        two = 2
        three = 3
        four = 4
        five = 5
    rating = models.IntegerField(choices=Ratings.choices, null=True)

    EnrollmentObject = EnrollmentManager()

    class meta:
        unique_together = ('subscriber', 'classroom')

    def __str__(self):
        return f'{str(self.classroom.__str__())}\'s Subscribers'

    def activate(self):
        self.is_active = True
        self.date_joined = timezone.now()
        self.save()
        return self

    def deactivate(self):
        self.is_active = False
        self.save()
        return self


# class ClassroomTag(models.Model):
#     classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
#     tag = models.ForeignKey(Category, on_delete=models.CASCADE, blank=False, null=False)
#     ClassroomTagObject = models.Manager()
#
#     class meta:
#         unique_together = ('classroom', 'tag')
