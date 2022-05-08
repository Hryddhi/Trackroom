from django.utils import timezone
from django.utils.crypto import get_random_string
from django.db import models

from accounts.models import Account


def get_list_of_joined_classroom(account, **kwargs):
    e = Enrollment.EnrollmentObject.filter(subscriber=account, is_active=True)
    return [x.classroom.pk for x in e]


def get_list_of_created_classroom(account):
    c = Classroom.ClassroomObject.filter(creator=account)
    return [classroom for classroom in c]


class ClassType (models.Model):
    PUBLIC, PRIVATE = 'Public', 'Private'

    CLASS_TYPE_CHOICES = [PUBLIC, PRIVATE]
    class_type = models.CharField(
        max_length=30, unique=True, primary_key=True)
    objects = models.Manager()

    def __str__(self):
        return self.pk


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

    def __str__(self):
        return self.pk


class ClassroomManager(models.Manager):

    def create(self, *args, **kwargs):
        classroom = super(ClassroomManager, self).create(*args, **kwargs)
        if classroom.class_type.pk == ClassType.PRIVATE:
            PrivateClassroom.PrivateClassroomObject.create(
                classroom=classroom, code=PrivateClassroom.get_unique_code())
        return classroom


class Classroom(models.Model):
    creator = models.ForeignKey(Account, on_delete=models.CASCADE, editable=False)
    title = models.CharField(unique=True, max_length=100)
    description = models.TextField(blank=True)
    class_type = models.ForeignKey(ClassType, on_delete=models.PROTECT)
    class_category = models.ForeignKey(ClassCategory, on_delete=models.CASCADE)

    ratings = models.FloatField(default="0")
    subscriber_count = models.IntegerField(default=0)
    date_created = models.DateTimeField(auto_now_add=True)

    @property
    def subscribers(self):
        sub = Enrollment.EnrollmentObject.filter(classroom=self, is_active=True)
        sub_list = [e.subscriber.pk for e in sub]
        return Account.objects.filter(pk__in=sub_list)

    def calc_ratings(self):
        enrollment = Enrollment.EnrollmentObject.filter(classroom=self)
        rating_list = [e.rating for e in enrollment if e.rating is not None]
        self._ratings = sum(rating_list) / len(rating_list)
        self.save()

    ClassroomObject = ClassroomManager()

    def __str__(self):
        return self.title

    def has_this_creator(self, account):
        return self.creator == account

    def has_this_subscriber(self, account):
        return Enrollment.EnrollmentObject.filter(classroom=self, subscriber=account, is_active=True).exists()

    def has_this_member(self, account):
        return self.has_this_creator(account) or self.has_this_subscriber(account)

    @staticmethod
    def get_created_classroom_of(account):
        return Classroom.ClassroomObject.filter(creator=account)

    @staticmethod
    def get_joined_classroom_of(account):
        return Classroom.ClassroomObject.filter(pk__in=get_list_of_joined_classroom(account))

    @staticmethod
    def get_recommendable_classroom_of(account):
        return Classroom.ClassroomObject.filter(class_type=ClassType.PUBLIC).exclude(pk__in=get_list_of_joined_classroom(account)).exclude(
            pk__in=[x.pk for x in get_list_of_created_classroom(account)]).order_by('-ratings', '-subscriber_count')


class PrivateClassroom(models.Model):
    classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
    CURRENT_CODE_LENGTH = 6     # configurable for future increase in length
    code = models.CharField(unique=True, null=True, max_length=10)

    PrivateClassroomObject = models.Manager()

    @staticmethod
    def does_code_exist(code):
        return PrivateClassroom.PrivateClassroomObject.filter(code=code).exists()

    @staticmethod
    def get_unique_code():
        while True:
            code = get_random_string(length=PrivateClassroom.CURRENT_CODE_LENGTH)
            if not PrivateClassroom.does_code_exist(code):
                break
        return code


class EnrollmentManager(models.Manager):

    def create(self, *args, **kwargs):
        classroom = kwargs.get('classroom')
        subscriber = kwargs.get('subscriber')
        qs = self.get_queryset().filter(classroom=classroom, subscriber=subscriber)
        enrollment = super(EnrollmentManager, self).create(*args, **kwargs) if not qs.exists() else qs[0].activate()
        classroom.subscriber_count = classroom.subscriber_count + 1
        classroom.save()
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

    def activate(self):
        self.is_active = True
        self.date_joined = timezone.now()
        self.save()
        return self

    def deactivate(self):
        self.is_active = False
        self.save()
        classroom = self.classroom
        classroom.subscriber_count = classroom.subscriber_count - 1
        classroom.save()
        return self


# class ClassroomTag(models.Model):
#     classroom = models.ForeignKey(Classroom, on_delete=models.CASCADE, blank=False, null=False)
#     tag = models.ForeignKey(Category, on_delete=models.CASCADE, blank=False, null=False)
#     ClassroomTagObject = models.Manager()
#
#     class meta:
#         unique_together = ('classroom', 'tag')
