//
//  DetailedClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI
import Alamofire

struct CreatorDetailedClassroomView: View {
    @State var isCreateNewPostActive: Bool = false
    @State var isInviteStudentsActive: Bool = false
    
    @State var postList: [PostList] = []
    
    var classPk: Int
    var className: String
    var classDescription: String
    var classRating: String
    var classCatagory: String
    
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                VStack {
                    ZStack {
                        Image("ClassIcon\(classPk % 5)")
                            .resizable()
                            .scaledToFill()
                            .frame(width: .infinity, height: 150, alignment: .center)
                            .clipped()
                            .blendMode(.screen)
                            .opacity(0.5)
                            .edgesIgnoringSafeArea(.all)
                        
                        VStack {
                            HStack {
                                if (classRating.contains("No Ratings Yet")) {
                                    Text("No Rating Yet • \(classCatagory)")
                                        .fontWeight(.bold)
                                        .padding(.horizontal, 16)
                                        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 20, idealHeight: 24, maxHeight: 30, alignment: .leading)
                                }
                                else {
                                    Text("\(classRating) ☆ • \(classCatagory)")
                                        .fontWeight(.bold)
                                        .padding(.horizontal, 16)
                                        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 20, idealHeight: 24, maxHeight: 30, alignment: .leading)
                                }
                                
                                Spacer()
                                
                                Image(systemName: "square.and.arrow.up")
                                    .font(Font.title3.weight(.bold))
                                    .frame(width: 40, height: 30, alignment: .leading)
                                    .foregroundColor(Color("BlackWhiteColor"))
                                    .onTapGesture {
                                        print("On Tab Gesture Leave Class")
                                        isInviteStudentsActive.toggle()
                                    }
                                    .sheet(isPresented: $isInviteStudentsActive) {
                                        InviteStudentView(classPk: classPk)
                                    }
                                
                            }
                            
                            Text(classDescription)
                                .padding(.leading)
                                .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 50, alignment: .leading)
                        }
                        
                        
                    }
                    .background(Color("ClassroomCardBgColor"))
                    .cornerRadius(10)
                    .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 3)
                    //.opacity(0.8)
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
                        CreateNewPostView(classPk: classPk)
                    }
                    .onTapGesture {
                        isCreateNewPostActive.toggle()
                    }

                    ForEach(postList, id: \.self) { result in
                        NavigationLink(destination: DetailedPostView(postPk: result.pk, postTitle: result.title, postDescription: result.description, postDate: result.date_created, postType: result.post_type)) {
                            PostCard(postTitle: result.title, dateCreated: result.date_created, postDescription: result.description)
                       }
                    }
                    
                }
            }
            
        }
        .navigationTitle(className)
        .onAppear(perform: getPostList)
    }
    
    func getPostList() {
        print("Inside Get Post List Function")
        
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        
        let GET_POST_LIST = "http://20.212.216.183/api/classroom/\(classPk)/timeline/"
        
        AF.request(GET_POST_LIST, method: .get, headers: headers).responseJSON { response in
            guard let data = response.data else { return }
            let status = response.response?.statusCode
            
            if let response = try? JSONDecoder().decode([PostList].self, from: data) {
                print("Get Post List Creator Success Status Code : \(String(describing: status))")
                debugPrint("Create Classrooom Response Data Decoded")
                postList = response
                print(postList)
                return
            }
            else {
                print("Get Post List Creator Fail Status Code : \(String(describing: status))")
                return
            }
        }
    }
}

//struct DetailedClassroomView_Previews: PreviewProvider {
//    static var previews: some View {
//        CreatorDetailedClassroomView(classPk: 2, className: "result.title", classRating: "result.ratings", classCatagory: "result.class_category")
//    }
//}
