import Foundation

public let TEST_APPLE_URL = "https://itunes.apple.com/search?term=taylor+swift&entity=song"

public let BASE_URL = "http://20.212.216.183/api/"
public let LOGIN_URL = "http://20.212.216.183/api/login"
public let REGISTER_URL = "http://20.212.216.183/api/register"
public let USER_INFO_URL = "http://20.212.216.183/api/account/u/"
public let USER_TOKEN_TEST = "http://20.212.216.183/api/test"
public let BLACKLIST_REFRESH = "http://20.212.216.183/api/logout/blacklist"
public let CHANGE_PASSWORD = "http://20.212.216.183/api/account/u/change-password/"
public let CHANGE_USER_INFO = "http://20.212.216.183/api/account/u/"
public let NOTIFICATION_LIST = "http://20.212.216.183/api/account/u/notification-list/"
public let PUBLIC_CLASSROOM_LIST = "http://20.212.216.183/api/account/u/joined-public-classroom-list/"
public let PRIVATE_CLASSROOM_LIST = "http://20.212.216.183/api/account/u/joined-private-classroom-list/"
public let CREATED_CLASSROOM_LIST = "http://20.212.216.183/api/account/u/created-classroom-list/"
public let CLASSROOM = "http://20.212.216.183/api/classroom/"


struct RegisterRequest : Encodable{
    let username: String
    let email: String
    let password: String
    let password2: String
}

struct LoginRequest : Encodable{
    let email: String
    let password: String
}

struct ChangePassword : Encodable{
    let new_password: String
    let new_password2: String
    let old_password: String
}

struct ChangeUserInfo : Encodable{
    let username: String?
    let bio: String?
    let profile_image: String?
}

struct ChangeProfileInfo : Encodable{
    let new_password: String
    let new_password2: String
    let old_password: String
}

struct CreateClassroom : Encodable{
    let title: String
    let description: String
    let class_type: String
    let class_category: String
}

struct JoinPrivateClassroom : Encodable{
    let code: String
}
