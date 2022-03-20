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
                
                TextField("Change Bio", text: $bio)
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
                
                Text("If you don not want to change only one you can leave the other text field empty and apply for changes.")
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
                        
                        AF.request(REGISTER_URL,
                                   method: .post,
                                   parameters: ,
                                   encoder: JSONParameterEncoder.default).response { response in

                            let status = response.response?.statusCode

                            print("Change Password Status : \(status)")
                            switch response.result{
                            case .success:
                                showModal.toggle()
                            case .failure(let error):
                                print(error)
                            }
                        }
                        
                        
                        showModal.toggle()
                    }
                                
            }
        }
    }
}

//struct EditProfileView_Previews: PreviewProvider {
//    @Binding var isActive: Bool
//    static var previews: some View {
//        EditProfileView(showModal: $isActive)
//    }
//}
