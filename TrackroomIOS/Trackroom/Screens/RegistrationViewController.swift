import SwiftUI

import SwiftUI
import Alamofire

struct Login: Encodable {
    let username: String
    let email: String
    let password: String
    let password2: String
}

struct LoginResponsex: Codable {
    let access: String
    let refresh: String
}

struct RegistrationViewController: View {
    @State var token = [LoginResponsex]()

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
                    .onTapGesture {
                        print("inside on tap gesture")
                        let login = Login(username: "rifatul", email: "rifatlynx@gmail.com", password: "123456", password2: "123456")
                        

                        AF.request(REGISTER_URL,
                                   method: .post,
                                   parameters: login,
                                   encoder: JSONParameterEncoder.default).response
                        { response in
//                            debugPrint("request made")
//                            guard let data = response.data else { return }
//                            debugPrint("request data save")
//                            if let response = try? JSONDecoder().decode(LoginResponsex.self, from: data) {
//                                debugPrint("decoded data")
//                                let token = response.token
//                                debugPrint("received token  : \(token) ")
//                            }
//                            else {
//                                let status = response.response?.statusCode
//                                print("\(status)")
//                                print("Failed to save request")
//                            }
                            let status = response.response?.statusCode
                            print("Register Respoonse : \(status)")
                        }

                    }

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
