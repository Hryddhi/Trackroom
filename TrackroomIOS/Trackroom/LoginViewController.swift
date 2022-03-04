import SwiftUI
import Foundation



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
                
                CustomDivider()
                
                loginForm()
            
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
    @State var email : String = ""
    @State var password : String = ""
    
    var body: some View {
//        CustomTextField(textFieldInput: email, textFieldLabel: "Email");
//        CustomSecureField( secureFieldInput: password, secureFieldLabel: "Password");
        
        TextField("Email", text: $email)
            .padding(.all, 32)
            .background(Color("WhiteGreyColor"))
            .foregroundColor(Color("BlackWhiteColor"))
            .frame(width: .infinity,
                   height: 50,
                   alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)
        
        SecureField("Password", text: $password)
            .padding(.all, 32)
            .background(Color("WhiteGreyColor"))
            .foregroundColor(Color("BlackWhiteColor"))
            .frame(width: .infinity,
                   height: 50,
                   alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)

        NavigationLink(destination: LoginLoadingViewController(email: $email, password: $password)) {
            CustomTapableButton(tapableButtonLable: "Login")
        }
        
    }
}

struct registrationPage: View {
    var body: some View {
        HStack {
            Text("Not A User ?")
                .fontWeight(.bold)
                .foregroundColor(Color("BlackWhiteColor"))
            NavigationLink(destination: RegistrationViewController()) {
                Text("Create An Account")
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
            }
        }
        .padding(.all, 8)
    }
}


