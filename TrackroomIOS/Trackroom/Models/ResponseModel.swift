import Foundation

struct RegisterResponse : Codable{
    let refresh: String
    let access: String
}

struct LoginResponse : Codable{
    let refresh: String
    let access: String
}

