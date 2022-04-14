import Foundation

struct LoginResponse : Codable{
    let refresh: String
    let access: String
}

struct getUserInfoResponse : Codable{
    let pk: Int
    let email: String
    let username: String
    let profile_image: String?
    let bio: String?
}

struct NotificationList : Hashable, Codable{
    let classroom: String
    let message: String
    let date: String
}

struct ClassroomList : Hashable, Codable{
    let pk: Int
    let creator: String
    let title: String
    let class_type: String
    let description: String
    let class_category: String
    let ratings: String
}

struct PostList : Hashable, Codable{
    let pk: Int
    let classroom: Int
    let title: String
    let description: String
    let date_created: String
    let post_type: String
}
