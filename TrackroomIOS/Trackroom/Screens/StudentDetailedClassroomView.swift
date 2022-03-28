//
//  StudentDetailedClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 27/3/22.
//

import SwiftUI
import Alamofire

struct StudentDetailedClassroomView: View {
    @State var className: String = "Default Classroom"
    @State var isCreateNewPostActive: Bool = false
    @State var leaveClassAlertVisible: Bool = false
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>

        
    var classPk: Int
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                VStack {
                    ZStack {
                        Image("ClassIcon5")
                            .resizable()
                            .scaledToFill()
                            .frame(width: .infinity, height: 150, alignment: .center)
                            .clipped()
                            .blendMode(.screen)
                            .edgesIgnoringSafeArea(.all)
                        
                        VStack {
                            HStack {
                                Text("Full Class Title Goes Here")
                                    .font(.title3)
                                    .fontWeight(.bold)
                                    .padding(.leading, 16)
                                
                                Spacer()
                                
                                Image(systemName: "rectangle.portrait.and.arrow.right")
                                    .font(Font.title3.weight(.bold))
                                    .foregroundColor(Color("PrimaryColor"))
                                    .frame(width: 40, height: 30, alignment: .leading)
                                    .onTapGesture {
                                        print("On Tab Gesture Leave Class")
                                        leaveClassAlertVisible = true
                                        //leaveClass()
                                    }
                                    .alert(isPresented: $leaveClassAlertVisible) {
                                        Alert(title: Text("Leave Class"), message: Text("Are you sure you want to leave this class?"), primaryButton: .destructive(Text("Leave"), action: {
                                            leaveClass()
                                        }), secondaryButton: .cancel())
                                    }
                            }

                            Text("4.5 ☆ • Cooking")
                                .fontWeight(.bold)
                                .padding(.horizontal, 16)
                                .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 20, idealHeight: 24, maxHeight: 30, alignment: .leading)
                        }
                    }
                    .background(Color("ClassroomCardBgColor"))
                    .cornerRadius(10)
                    .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 3)
                    .opacity(0.8)
                    .padding(.horizontal)

                    ZStack {
                        HStack {
                            Image("LuffyProfilePicture")
                                .resizable()
                                .frame(width: 60, height: 50, alignment: .center)
                                .clipShape(Circle())
                                .padding(.leading)
                            
                            Spacer()
                            
                            Text("Create A New Post!")
                                .foregroundColor(Color("BlackWhiteColor"))
                                .font(.callout)
                                .fontWeight(.bold)
                                .frame(minWidth: 200, idealWidth: .infinity, maxWidth: .infinity, minHeight: 20, idealHeight: 25, maxHeight: 30, alignment: .leading)
                        }
                    }
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 80, idealHeight: 100, maxHeight: 120, alignment: .center)
                    .background(Color("LightGreyColor"))
                    .cornerRadius(10)
                    .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 3)
                    .padding(.horizontal)
                    .sheet(isPresented: $isCreateNewPostActive) {
                        StudentRatingView()
                    }
                    .onTapGesture {
                        isCreateNewPostActive.toggle()
                    }
                    
                    PostCard()
                    PostCard()
                    PostCard()
                    PostCard()
                    PostCard()
                    PostCard()
                    PostCard()
                }
            }
            
        }
        .navigationTitle("Classroom Name")
    }
    
    func leaveClass() {
        var LEAVE_CLASSROOM = "http://20.212.216.183/api/classroom/\(classPk)/leave/"
        
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(LEAVE_CLASSROOM,
                   method: .post,
                   headers: header).response { response in
            let status = response.response?.statusCode
            print("Status Code \(String(describing: status))")
            switch response.result{
            case .success:
                print("Classroom has been left sucessfully")
                self.presentationMode.wrappedValue.dismiss()
            case .failure(let error):
                print(error)
            }
        }
    }
}

struct StudentDetailedClassroomView_Previews: PreviewProvider {
    static var previews: some View {
        StudentDetailedClassroomView(classPk: 1)
    }
}
