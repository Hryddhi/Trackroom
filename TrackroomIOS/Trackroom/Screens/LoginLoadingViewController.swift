//
//  LoadingViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI
import Alamofire

struct LoginLoadingViewController: View {
    @State var loginRequest = [LoginRequest]()
    @State var showHomeScreen = false

    @Binding var email: String
    @Binding var password: String
    
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ProgressView("Logging You In!")
                .onAppear(perform: login)
                .sheet(isPresented: $showHomeScreen) {
                            HomeViewController()
                        }
            
            
        }
        .navigationBarHidden(true)
    }
    
    
    private func login() {
    
        print("Inside Login Function")
        let loginRequest = LoginRequest(email: "rifat@gmail.com", password: "rifat")
        AF.request(LOGIN_URL,
                   method: .post,
                   parameters: loginRequest,
                   encoder: JSONParameterEncoder.default).response { response in
            print("Login Function Request Sucessfull")
            guard let data = response.data else { return }
            print("Login Function Request Saved to Data")
            if let response = try? JSONDecoder().decode(LoginResponse.self, from: data) {
                debugPrint("Login Request Response Data Decoded")
                let access = response.access
                let refresh = response.refresh
                print("received access  : \(access) ")
                print("received refresh  : \(refresh) ")
                showHomeScreen = true
                return
            }
            else {
                let status = response.response?.statusCode
                print("Status Code : \(status)")
                print("Failed to save request")
                return
            }
        }
        
    }
}

struct LoadingViewController_Previews: PreviewProvider {
    @State static var email: String = ""
    @State static var password: String = ""

    static var previews: some View {
        LoginLoadingViewController(email: $email, password: $password)
    }
}
