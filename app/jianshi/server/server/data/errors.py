# User releated error
class UnknownError(Exception):
    rc = 1
    msg = 'An error has occurred when processing the request.'


class DBError(Exception):
    rc = 2
    msg = 'An error has occurred at DB layer'


class AccessForbiddenError(Exception):
    rc = 403
    msg = 'Forbidden'


class ArgumentShouldNotBeNull(Exception):
    rc = 700
    msg = 'Illegal argument given'

# User related


class AuthTokenInvalid(Exception):
    rc = 1000
    msg = "User auth token is invalid"


class UserCreateFailure(Exception):
    rc = 1001
    msg = "User create failure"


class UserLoginFailure(Exception):
    rc = 1002
    msg = "User login failure"


class UserNotFound(Exception):
    rc = 1003
    msg = "User not found"


class WrongPassword(Exception):
    rc = 1004
    msg = "Your original password is not correct"


class UserEmailAlreadyUsed(Exception):
    rc = 1005
    msg = "This user email is already used. Please choose a new one if that's not yourself"


class CanNotFindUserId(Exception):
    rc = 1006
    msg = "Cannot find your user id"


class UserDeleteFailure(Exception):
    rc = 1007
    msg = "User delete error"


class EmailFormatWrong(Exception):
    rc = 1008
    msg = "Email format is wrong"


# Diary related
class InvalidUserIdDuringCreatingDiary(Exception):
    rc = 2000
    msg = "User Id is invalid during creating diart"


class DiaryEmpty(Exception):
    rc = 2001
    msg = "The target diary is empty"


class NotAccessForThisDiary(Exception):
    rc = 2002
    msg = "Not access for thie diary"


class UpdateDiaryFailure(Exception):
    rc = 2003
    msg = "Update diary failure"


class NoAccessForOthersDiary(Exception):
    rc = 2004
    msg = "You have no access for other's diary"


# DB
class DbCreateError(Exception):
    rc = 3000
    msg = "DB Creation error"


class DbUpdateError(Exception):
    rc = 3001
    msg = "DB Update error"


class DbDeleteError(Exception):
    rc = 3002
    msg = "Db Deletion error"


class DbReadError(Exception):
    rc = 3003
    msg = "Db Read Error"
