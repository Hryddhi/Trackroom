import Foundation

public let BASE_URL = "http://20.212.216.183/api/"
public let LOGIN_URL = "http://20.212.216.183/api/login"
public let REGISTER_URL = "http://20.212.216.183/api/register"
public let USER_INFO_URL = "http://20.212.216.183/api/account/u/"
public let USER_TOKEN_TEST = "http://20.212.216.183/api/test"

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

