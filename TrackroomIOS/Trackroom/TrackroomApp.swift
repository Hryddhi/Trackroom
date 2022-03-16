//
//  TrackroomApp.swift
//  Trackroom
//
//  Created by Rifatul Islam on 15/2/22.
//

import SwiftUI
import Alamofire

@main
struct TrackroomApp: App {
    var body: some Scene {
        WindowGroup {
                LoginLoadingViewController()
        }
    }
}
        
//    private func isLoggedIn(){
//        var success: Bool = false
//        let access = UserDefaults.standard.string(forKey: "access")
//        print("Access Token : \(access)")
//        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
//        AF.request(USER_TOKEN_TEST, method: .post, headers: headers).responseJSON { response in
//            print("Inside Is logged in function")
//            let status = response.response?.statusCode
//            print("Status Code Saved")
//            print("Status Code is : \(status)")
//            switch response.result{
//                case .success:
//                    success = true
//                case .failure(let error):
//                    success = false
//            }
//        }
//    }

