//
//  LoadingViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI
import Alamofire

struct LoginLoadingView: View {
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
                HomeView()
            }
            else if (authFail){
                WelcomeViewController()
            }
        }
        .navigationBarHidden(true)
    }
    
    private func isLoggedIn(){
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

struct LoadingViewController_Previews: PreviewProvider {
    static var previews: some View {
        LoginLoadingView()
    }
}
