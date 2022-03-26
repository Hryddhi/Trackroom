//
//  LoadingViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI
import Alamofire

struct LoginLoadingView: View {
    @State public var authSuccess: Bool = false
    @State public var authFail: Bool = false

    var body: some View {
        NavigationView {
            ZStack{
                Color("BgColor")
                    .edgesIgnoringSafeArea(.all)
                ProgressView("Loading")
                    .onAppear {
                        isLoggedIn()
                    }
                
                NavigationLink(destination: HomeView(), isActive: .constant(authSuccess)) {
                   Text("")
                 }
                
                NavigationLink(destination: WelcomeViewController(), isActive: .constant(authFail)) {
                   Text("")
                 }
                
//                if(authSuccess) {
//                    HomeView()
//                }
//                else if (authFail){
//                    WelcomeViewController()
//                }
            }
            .navigationBarHidden(true)
        }
    }
    
    private func isLoggedIn(){
        let access = UserDefaults.standard.string(forKey: "access")
        print("Access Token : \(access)")
        if(access != nil) {
            let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
            AF.request(USER_TOKEN_TEST, method: .post, headers: headers).responseJSON { response in
                let status = response.response?.statusCode
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
        else {
            authFail.toggle()
        }
        
    }
}

struct LoadingViewController_Previews: PreviewProvider {
    static var previews: some View {
        LoginLoadingView()
    }
}
