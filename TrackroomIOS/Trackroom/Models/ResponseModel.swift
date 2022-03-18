import Foundation

struct LoginResponse : Codable{
    let refresh: String
    let access: String
}

struct getUserInfoResponse : Codable{
    let email: String
    let username: String
    let profile_image: String?
}

struct NotificationList : Codable{
    let classroom: String
    let message: String
    let date: String
}
