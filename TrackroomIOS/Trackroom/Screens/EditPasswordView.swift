//
//  EditPasswordView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 18/3/22.
//

import SwiftUI
import Alamofire

struct EditPasswordView: View {
    @Binding var showModal: Bool
    @State var currentPassword: String = ""
    @State var newPassword: String = ""
    @State var newPassword2: String = ""
    
    
    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack{
                Text("Edit Profile Information")
                    .fontWeight(.bold)
                    .padding(.leading)
                    .font(.title3)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 30,
                           idealHeight: 40,
                           maxHeight: 50,
                           alignment: .center)
                    .padding(.top, 32)
                
                Image("LuffyProfilePicture")
                    .resizable()
                    .frame(width: 150, height: 130, alignment: .top)
                    .clipShape(Circle())
                    .padding(.bottom)
                
                SecureField("Current Password", text: $currentPassword)
                    .padding(.all, 32)
                    .background(Color("WhiteGreyColor"))
                    .foregroundColor(Color("BlackWhiteColor"))
                    .frame(width: .infinity,
                           height: 50,
                           alignment: .leading)
                    .cornerRadius(32)
                    .shadow(radius: 4)
                    .padding(.horizontal, 16)
                    .disableAutocorrection(true)
                
                SecureField("New Password", text: $newPassword)
                    .padding(.all, 32)
                    .background(Color("WhiteGreyColor"))
                    .foregroundColor(Color("BlackWhiteColor"))
                    .frame(width: .infinity,
                           height: 50,
                           alignment: .leading)
                    .cornerRadius(32)
                    .shadow(radius: 4)
                    .padding(.horizontal, 16)
                    .textInputAutocapitalization(.never)
                    .keyboardType(.emailAddress)
                    .disableAutocorrection(true)
                
                SecureField("Re-Type New Password", text: $newPassword2)
                    .padding(.all, 32)
                    .background(Color("WhiteGreyColor"))
                    .foregroundColor(Color("BlackWhiteColor"))
                    .frame(width: .infinity,
                           height: 50,
                           alignment: .leading)
                    .cornerRadius(32)
                    .shadow(radius: 4)
                    .padding(.horizontal, 16)
                    .textInputAutocapitalization(.never)
                    .keyboardType(.emailAddress)
                    .disableAutocorrection(true)
                
                Text("You need to input your current password and a set of new passwrods to successfully change your password")
                    .padding(.all, 16)
                    .foregroundColor(Color("ShadowColor"))
                    .font(.caption)
                
                Text("Submit")
                    .font(.body)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding()
                    .onTapGesture {
                        
                        print("inside on tap gesture")
                        let changePassword = ChangePassword(new_password: newPassword,
                                                            new_password2: newPassword2,
                                                            old_password: currentPassword)
                        
                        let access = UserDefaults.standard.string(forKey: "access")
                        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
                        
                        AF.request(CHANGE_PASSWORD,
                                   method: .put,
                                   parameters: changePassword,
                                   encoder: JSONParameterEncoder.default,
                                   headers: headers).response { response in
                            
                            let status = response.response?.statusCode
                            
                            print("Change Password Status : \(status)")
                            switch response.result{
                            case .success:
                                showModal.toggle()
                            case .failure(let error):
                                print(error)
                            }
                        }
                    }
            }
        }
    }
}

//struct EditPasswordView_Previews: PreviewProvider {
//    static var previews: some View {
//        EditPasswordView()
//    }
//}
