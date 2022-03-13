from django.utils import timezone
from django.db import models

from accounts.models import Account


def get_list_of_joined_classroom(account):
    e = Enrollment.EnrollmentObject.filter(subscriber=account)
    return [x.classroom.pk for x in e]


class ClassType (models.Model):
    class_type = models.CharField(
        max_length=30, unique=True, primary_key=True,
        blank=False, null=False)
    objects = models.Manager()


class Classroom (models.Model):
    creator = models.ForeignKey(Account, on_delete=models.CASCADE, blank=False, null=False)
    title = models.CharField(unique=True, max_length=100, blank=False, null=False)
    description = models.CharField(max_length=255, blank=True)
    class_type = models.ForeignKey(ClassType, on_delete=models.PROTECT)
    date_created = models.DateTimeField(auto_now_add=True)

    @property
    def subscribers(self):
        return Enrollment.EnrollmentObject.filter(classroom=self)

    @property
    def ratings(self):
        enrollment = Enrollment.EnrollmentObject.filter(classroom=self)
        rating_list = [e.rating for e in enrollment if e.rating is not None]
        return sum(rating_list)/len(rating_list)

    ClassroomObject = models.Manager()

    def __str__(self):
        return self.title

    def has_this_creator(self, account):
        return self.creator == account

    def has_this_subscriber(self, account):
        entry = self.subscribers.filter(subscriber=account)
        if entry.exists():
            return entry[0].is_active
        return False

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

    @staticmethod
    def does_code_exist(code):
        return PrivateClassroom.PrivateClassroomObject.filter(code=code).exists()


class EnrollmentManager(models.Manager):

    def create(self, *args, **kwargs):
        classroom = kwargs.get('classroom')
        subscriber = kwargs.get('subscriber')
        qs = self.get_queryset().filter(classroom=classroom, subscriber=subscriber)
        if not qs.exists():
            enrollment = super(EnrollmentManager, self).create(*args, **kwargs)
        else:
            enrollment = qs[0]
            enrollment.is_active = True
            enrollment.date_joined = timezone.now()
            enrollment.save()
        return enrollment

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


class Category(models.Model):
    category_name = models.CharField(
        max_length=30, unique=True, primary_key=True,
        blank=False, null=False)
    objects = models.Manager()


class ClassroomTag(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
    tag = models.ForeignKey(Category, on_delete=models.CASCADE, blank=False, null=False)
    ClassroomTagObject = models.Manager()

    class meta:
        unique_together = ('classroom', 'tag')
