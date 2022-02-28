import SwiftUI

import SwiftUI

struct RegistrationViewController: View {
    var body: some View {
        ZStack {
            Color("BgColor")
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
                
                CustomDivider()
                
                registrationForm()
                
                CustomTapableButton(tapableButtonLable: "Register")
                
                loginPage()
            }
        }
        .navigationBarHidden(true)

    }
}

struct registrationForm: View {
    private var fullName : String = ""
    private var username : String = ""
    private var password : String = ""
    private var password2 : String = ""
    
    var body: some View {
        CustomTextField(textFieldInput: username, textFieldLabel: "Full Name")
        CustomTextField(textFieldInput: username, textFieldLabel: "Email")
        CustomSecureField(secureFieldInput: password, secureFieldLabel: "Password")
        CustomSecureField(secureFieldInput: password2, secureFieldLabel: "Re-Type Password")
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

struct loginPage: View {
    var body: some View {
        HStack {
            Text("Already A User?")
                .fontWeight(.bold)
            NavigationLink(destination: LoginViewController()) {
                Text("Login To Account")
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
            }
        }
        .padding()
    }
}

struct RegistrationScreen_Previews: PreviewProvider {
    static var previews: some View {
        RegistrationViewController()
    }
}
