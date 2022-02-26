from rest_framework.exceptions import APIException


class CustomNotFoundError(APIException):

    status_code = 404
    default_code = 'NOT_FOUND'

    def __init__(self, message, cause=None):
        self._cause = cause
        self.default_detail = {"error": message}
        super().__init__()

class EmailNotSentException(APIException):

    status_code = 500
    default_code = 'EMAIL_NOT_SENT'

    def __init__(self, message, cause=None):
        self._cause = cause
        self.default_detail = {"error": message}
        super().__init__()