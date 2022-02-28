//
//  LoginScreen.swift
//  Trackroom
//
//  Created by Rifatul Islam on 16/2/22.
//

import SwiftUI
import Alamofire

//class TestLogin : ObservedObject{
//
//    @Published var post: TestModel
//
//    func getPost() {
//
//        guard let url = URL(string: "https://jsonplaceholder.typicode.com/posts/1") else { return }
//
//        URLSession.shared.dataTask(with: url) { data, response, error in
//            guard
//                let data = data,
//                error == nil,
//                let response = response as? HTTPURLResponse,
//                response.statusCode >= 200 && response.statusCode <= 299 else {
//                print("No Data")
//                return
//            }
//
//
//        }
//    }
//}

struct LoginViewController: View {
    //@StateObject var testLogin = TestLogin()
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack {
                Text("Login In To Your Account")
                    .font(.title)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                
                Image("LoginBanner")
                    .resizable()
                    .scaledToFit()
                    .frame(minWidth: 200,
                           idealWidth: 300,
                           maxWidth: 400,
                           minHeight: 200,
                           idealHeight: 300,
                           maxHeight: 400,
                           alignment: .center)
                    .padding()
                
                loginInWithGoogle()
                
                CustomDivider()
                
                loginForm()
                
                CustomTapableButton(tapableButtonLable: "Login")
                
                registrationPage()
            }
        }
        .navigationBarHidden(true)
    }
}

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginViewController()
    }
}

struct loginInWithGoogle: View {
    var body: some View {
        HStack{
            Image("GoogleIcon")
                .resizable()
                .frame(width: 23, height: 23, alignment: .leading)
                .padding(.horizontal)
            Spacer()
            
            Text("Login In With Google")
                .font(.system(size: 18))
                .fontWeight(.bold)
                .foregroundColor(Color("BlackWhiteColor"))
            
            Spacer()
        }
        .padding()
        .background(Color("WhiteGreyColor"))
        .frame(width: .infinity, height: 50, alignment: .center)
        .cornerRadius(32)
        .padding(.horizontal, 16)
        .shadow(radius: 3)
    }
}



struct loginForm: View {
    private var email : String = ""
    private var password : String = ""
    var body: some View {
        CustomTextField(textFieldInput: email, textFieldLabel: "Email");
        CustomSecureField( secureFieldInput: password, secureFieldLabel: "Password");

    }
}

struct registrationPage: View {
    var body: some View {
        HStack {
            Text("Not A User ?")
                .fontWeight(.bold)
                .foregroundColor(Color("BlackWhiteColor"))
            NavigationLink(destination: HomeViewController()) {
                Text("Create An Account")
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
            }
        }
        .padding(.all, 8)
    }
}


