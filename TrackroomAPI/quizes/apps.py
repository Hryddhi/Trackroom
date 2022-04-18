from django.apps import AppConfig
from django.db.models.signals import post_migrate


class QuizesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'quizes'

    def ready(self):
        from classrooms.models import Classroom
        from .models import Quiz, Question, Option, AssignedQuiz, assign_this_quiz_to_respective_subscribers
        from notifications.models import Notification, create_notification_for

        def create_test_quiz(sender, **kwargs):
            if not Quiz.QuizObject.filter(classroom=3, title='Test Quiz 1').exists():
                classroom = Classroom.ClassroomObject.get(pk=3)
                quiz = Quiz.QuizObject.create(
                    classroom=classroom,
                    title='Test Quiz 1',
                    description='This is the description for Test Quiz 1',
                    start_time="19-04-2022 02:20",
                    end_time="19-04-2022 03:50",
                )
                create_notification_for(quiz)
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

                assign_this_quiz_to_respective_subscribers(quiz)

        post_migrate.connect(create_test_quiz, sender=self)
