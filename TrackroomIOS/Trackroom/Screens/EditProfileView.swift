//
//  EditProfileView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 17/3/22.
//

import SwiftUI
import Alamofire

struct EditProfileView: View {
    @Binding var showModal: Bool
    @State var fullName: String = ""
    @State var bio: String = ""

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
                
                TextField("Change Full Name", text: $fullName)
                    .padding(.all, 16)
                    .padding(.horizontal, 35)
                    .background(Color("WhiteGreyColor"))
                    .foregroundColor(Color("BlackWhiteColor"))
                    .frame(width: .infinity,
                           height: 50,
                           alignment: .leading)
                    .cornerRadius(32)
                    .shadow(radius: 4)
                    .padding(.horizontal, 16)
                    .disableAutocorrection(true)
                    .overlay(
                        HStack{
                            Image(systemName: "person.fill")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                TextField("Change Bio", text: $bio)
                    .padding(.all, 16)
                    .padding(.horizontal, 35)
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
                    .overlay(
                        HStack{
                            Image(systemName: "bookmark.fill")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                Text("If you don not want to change only one you can leave the other text field empty and apply for changes.")
                    .padding(.all, 16)
                    .foregroundColor(Color("ShadowColor"))
                    .font(.caption)
                
                Text("Submit")
                    .font(.title3)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding()
                    .onTapGesture {
                        print("inside on tap gesture")
                        
                        var userInfo = ChangeUserInfo(username: nil, bio: nil, profile_image: nil)

                        if(fullName.count > 0 && bio.count > 0) {
                            userInfo = ChangeUserInfo(username: fullName, bio: bio, profile_image: nil)
                        }
                        else if (fullName.count > 0 ){
                            userInfo = ChangeUserInfo(username: fullName, bio: nil, profile_image: nil)
                        }
                        else if (bio.count > 0) {
                            userInfo = ChangeUserInfo(username: nil, bio: bio, profile_image: nil)
                        }
                        
                        print(userInfo)
                        
                        let access = UserDefaults.standard.string(forKey: "access")
                        let header: HTTPHeaders = [.authorization(bearerToken: access!)]

                        AF.request(CHANGE_USER_INFO,
                                   method: .put,
                                   parameters: userInfo,
                                   encoder: JSONParameterEncoder.default,
                                   headers: header).response { response in

                            let status = response.response?.statusCode

                            print("Change Prof. Info Status : \(status)")
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
