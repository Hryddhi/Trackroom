//
//  LoadingViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI
import Alamofire

struct LoginLoadingViewController: View {
    @State var authSuccess: Bool = false
    @State var authFail: Bool = false

    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ProgressView("Loading")
                .onAppear {
                    isLoggedIn()
                }
            
            if(authSuccess) {
                HomeViewController()
            }
            else if (authFail){
                WelcomeViewController()
            }
            
//            NavigationLink(destination: HomeViewController()) {
//                Text("Click me")
//            }
//
//            NavigationLink(destination: WelcomeViewController(),
//               isActive: self.$authFail) {
//
//            }.hidden()
        }
        .navigationBarHidden(true)
    }
    
    private func isLoggedIn(){
        //        var success: Bool = false
        let access = UserDefaults.standard.string(forKey: "access")
        print("Access Token : \(access)")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(USER_TOKEN_TEST, method: .post, headers: headers).responseJSON { response in
            print("Inside Is logged in function")
            let status = response.response?.statusCode
            print("Status Code Saved")
            print("Status Code is : \(status)")
            if (status == 200) {
                authSuccess.toggle()
                print("Auth Success Toggeled")
            }
            else {
                authFail.toggle()
                print("Auth Fail Toggeled")
            }
        }
    }
}

//    private func login() {
//
//        print("Inside Login Function")
//        let loginRequest = LoginRequest(email: "rifat@gmail.com", password: "rifat")
//        AF.request(LOGIN_URL,
//                   method: .post,
//                   parameters: loginRequest,
//                   encoder: JSONParameterEncoder.default).response { response in
//            print("Login Function Request Sucessfull")
//            guard let data = response.data else { return }
//            print("Login Function Request Saved to Data")
//            if let response = try? JSONDecoder().decode(LoginResponse.self, from: data) {
//                debugPrint("Login Request Response Data Decoded")
//                let access = response.access
//                let refresh = response.refresh
//                print("received access  : \(access) ")
//                print("received refresh  : \(refresh) ")
//                showHomeScreen = true
//                return
//            }
//            else {
//                let status = response.response?.statusCode
//                print("Status Code : \(status)")
//                print("Failed to save request")
//                return
//            }
//        }
//        
//    }

struct LoadingViewController_Previews: PreviewProvider {
    static var previews: some View {
        LoginLoadingViewController()
    }
}
