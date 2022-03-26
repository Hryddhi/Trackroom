//
//  JoinPrivateClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI
import Alamofire

struct JoinPrivateClassroomView: View {
    @Binding var isActive: Bool
    @State var joinPrivareClassroomCode: String = ""
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack {
                Text("Enroll To A Private Course")
                    .font(.title2)
                    .fontWeight(.bold)
                    .padding()
                TextField("Enter Your Private Course Code Here", text: $joinPrivareClassroomCode)
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
                            Image(systemName: "qrcode")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                Text("Submit")
                    .font(.title3)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding()
                    .onTapGesture {
                        joinPrivateClassroom()
                    }
                
            }
            .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 500, idealHeight: .infinity, maxHeight: .infinity, alignment: .top)
        }
    }
    
    func joinPrivateClassroom() {
        let access = UserDefaults.standard.string(forKey: "access")
        
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        let joinPrivateClassroom = JoinPrivateClassroom(code: joinPrivareClassroomCode)
        
        print("Join Private Classroom Code : \(joinPrivareClassroomCode)")
        print("Join Private Classroom Struct : \(joinPrivateClassroom)")
        
        AF.request(CLASSROOM,
                   method: .post,
                   parameters: joinPrivateClassroom,
                   encoder: JSONParameterEncoder.default,
                   headers: header).responseJSON { response in
            
            let status = response.response?.statusCode
            print("Status Code : \(status)")
            
            switch response.result{
                
            case .success:
                isActive.toggle()
                print("Classroom has been joined sucessfully")
                
            case .failure(let error):
                print(error)
            }
        }
    }
}
//
//struct JoinPrivateClassroomView_Previews: PreviewProvider {
//    static var previews: some View {
//        JoinPrivateClassroomView()
//    }
//}
