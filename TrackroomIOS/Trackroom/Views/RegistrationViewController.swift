//
//  RegistrationScreen.swift
//  Trackroom
//
//  Created by Rifatul Islam on 17/2/22.
//

import SwiftUI

//import SwiftUI
//
//struct RegistrationViewController: View {
//    var body: some View {
//        ZStack{
//            registerView()
//        }
//    }
//}
//
//struct RegistrationScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        RegistrationViewController()
//    }
//}

struct registerView: View {
    var body: some View {
        ZStack {
            Color("GreyColor")
                .edgesIgnoringSafeArea(.all)
            
            VStack{
                Text("Create Account")
                   .font(.title)
                   .fontWeight(.bold)
                   .foregroundColor(Color("PrimaryColor"))
               
                Image("RegistrationBanner")
                   .resizable()
                   .scaledToFit()
                   .frame(minWidth: 200, idealWidth: 300, maxWidth: 400, minHeight: 200, idealHeight: 300, maxHeight: 400, alignment: .center)
                   .padding(.all, 32)
                
                Rectangle()
                    .frame(width: .infinity, height: 2, alignment: .center)
                    .foregroundColor(Color.gray)
                    .padding(.all, 16)
                UserInfo()
                
                Register()
                
                HStack {
                    Text("Already A User")
                        .fontWeight(.bold)
                    Text("Login To Account")
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                }
                .padding()
            }
        }
    }
}

struct UserInfo: View {
    @State private var username : String = ""
    @State private var password : String = ""
    @State private var password2 : String = ""
    
    var body: some View {
        TextField("First Name & Last Name", text: $username)
            .padding(.all, 32)
            .background(Color.white)
            .foregroundColor(Color("SecondaryColor"))
            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 45, idealHeight: 50, maxHeight: 50, alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 3)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)
        
        TextField("Password", text: $password)
            .padding(.all, 32)
            .background(Color.white)
            .foregroundColor(Color("SecondaryColor"))
            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 45, idealHeight: 50, maxHeight: 50, alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 3)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)

        TextField("Re-Type Password", text: $password2)
            .padding(.all, 32)
            .background(Color.white)
            .foregroundColor(Color("SecondaryColor"))
            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 45, idealHeight: 50, maxHeight: 50, alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 3)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)

    }
}

struct Register: View {
    var body: some View {
        Text("Create Account")
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
            .shadow(radius: 3)
            .padding(.horizontal, 16)
            .padding(.vertical, 8)

    }
}
