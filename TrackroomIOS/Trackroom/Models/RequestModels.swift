import Foundation

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

