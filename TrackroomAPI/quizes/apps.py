from django.apps import AppConfig
from django.db.models.signals import post_migrate


class QuizesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'quizes'

    def ready(self):
        from classrooms.models import Classroom
        from .models import Quiz, Question, Option
        from notifications.models import Notification, ModelType

        def create_test_quiz(sender, **kwargs):
            if not Quiz.QuizObject.filter(classroom=3, title='Test Quiz 1').exists():
                classroom = Classroom.ClassroomObject.get(pk=3)
                quiz = Quiz.QuizObject.create(
                    classroom=classroom,
                    title='Test Quiz 1',
                    description='This is the description for Test Quiz 1'
                )
                Notification.NotificationObject.create(
                    related_classroom=classroom,
                    related_model_type=ModelType.objects.get(pk=ModelType.QUIZ),
                    date_created=quiz.date_created,
                    related=quiz
                )
                question_set = [
                    {'question':
                        Question.QuestionObject.create(
                        quiz=quiz,
                        question="What is 1 + 2?"
                        )
                    },
                    {'question':
                        Question.QuestionObject.create(
                        quiz=quiz,
                        question="What is 2 + 2?"
                        )
                    }
                ]
                for question in question_set:
                    question['option'] = []
                    for x in [1, 2, 3, 4]:
                         option = Option.OptionObject.create(
                                question=question['question'],
                                option=f"{x}",
                                 )
                         question['option'].append(option)

                    print(f"Options for {question['question'].question} is created")

                question_set[0]['option'][2].is_correct = True
                question_set[0]['option'][2].save()
                question_set[1]['option'][3].is_correct = True
                question_set[1]['option'][3].save()

        post_migrate.connect(create_test_quiz, sender=self)
