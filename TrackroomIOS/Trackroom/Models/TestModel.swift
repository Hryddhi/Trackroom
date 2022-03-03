import Foundation
import SwiftUI

struct TestModel : Identifiable, Codable{
    let userId: Int
    let id: Int
    let title: String
    let body: String
}
