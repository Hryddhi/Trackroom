//
//  LoginScreen.swift
//  Trackroom
//
//  Created by Rifatul Islam on 16/2/22.
//

import SwiftUI

struct LoginViewController: View {
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
                
                Rectangle()
                    .frame(width: .infinity, height: 2, alignment: .center)
                    .foregroundColor(Color.gray)
                    .padding(.all, 16)
                
                loginFilds()
                login()
                
                createAccount()
            }
        }
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

struct loginFilds: View {
    @State private var email : String = ""
    @State private var password : String = ""

    var body: some View {
        TextField("Email", text: $email)
            .padding(.all, 32)
            .background(Color("WhiteGreyColor"))
            .foregroundColor(Color("WhiteGreyColor"))
            .frame(width: .infinity,
                   height: 50,
                   alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)

        TextField("Password", text: $password)
            .padding(.all, 32)
            .background(Color("WhiteGreyColor"))
            .foregroundColor(Color("WhiteGreyColor"))
            .frame(width: .infinity,
                   height: 50,
                   alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)
            .padding(.top, 8)

    }
}

struct login: View {
    var body: some View {
        Text("Login")
            .font(.title2)
            .fontWeight(.bold)
            .foregroundColor(.white)
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                minHeight: 0,
                maxHeight: 50,
                alignment: .center
            )
            .background(Color("PrimaryColor"))
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)
            .padding(.top, 8)
            }
}

struct createAccount: View {
    var body: some View {
        HStack {
            Text("Not A User")
                .fontWeight(.bold)
                .foregroundColor(Color("BlackWhiteColor"))

            Text("Create An Account")
                .fontWeight(.bold)
                .foregroundColor(Color("PrimaryColor"))
        }
        .padding(.all, 8)
    }
}
