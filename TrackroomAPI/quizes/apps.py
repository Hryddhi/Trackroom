from django.apps import AppConfig
from django.db.models.signals import post_migrate


class QuizesConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'quizes'

    def ready(self):
        from classrooms.models import Classroom
        from .models import Quiz, Question, Option

        def create_test_quiz(sender, **kwargs):
            if not Quiz.QuizObject.filter(classroom=3, title='Test Quiz 2').exists():
                quiz = Quiz.QuizObject.create(
                    classroom=Classroom.ClassroomObject.get(pk=3),
                    title='Test Quiz 2',
                )
                questions = [
                    Question.QuestionObject.create(
                    quiz=quiz,
                    question="What is 1 + 2?"
                    ),
                    Question.QuestionObject.create(
                        quiz=quiz,
                        question="What is 2 + 2?"
                    )
                ]
                for question in questions:
                    for x in [1, 2, 3, 4]:
                        Option.OptionObject.create(
                            question=question,
                            option=f"{x}",
                            position=x
                        )
                    print(f"Options for {question.question} is created")

        post_migrate.connect(create_test_quiz, sender=self)
