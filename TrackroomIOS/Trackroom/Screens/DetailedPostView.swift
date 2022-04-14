//
//  DetailedPostView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 3/4/22.
//

import SwiftUI
import Alamofire

struct DetailedPostView: View {
    @State var comment: String = ""
   
    
    var postPk: Int
    var postAuthor: String = "Rifaul Islam Ramim"
    var postTitle: String
    var postDescription: String
    var postDate: String
    var postType: String
    
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                
                HStack {
                    Image("LuffyProfilePicture")
                        .resizable()
                        .frame(width: 60, height: 50, alignment: .top)
                        .clipShape(Circle())
                        .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 0)
                        .padding(.leading, 16)
                                        
                    VStack(alignment: .leading) {
                        Text (postAuthor)
                            .fontWeight(.bold)
                        
                        Text(postDate)
                            .foregroundColor(.gray)
                    }
                    
                    Spacer()

                }
                
                CustomDivider()
                
                Text(postDescription)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, alignment: .leading)
                    .padding(.horizontal)
                
                Text ("Class Comments")
                    .fontWeight(.bold)
                    .foregroundColor(.gray)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 35, idealHeight: 40, maxHeight: 40, alignment: .leading)
                    .padding()
                
                ForEach(0..<3) { i in
                    HStack(alignment: .top) {
                        Image("LuffyProfilePicture")
                            .resizable()
                            .frame(width: 50, height: 40, alignment: .top)
                            .clipShape(Circle())
                            .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 0)
                            .padding(.leading, 16)
                                            
                        VStack(alignment: .leading) {
                            Text ("Rifatul Islam Ramim")
                                .fontWeight(.bold)
                                .padding(.vertical, 1)
                            
                            Text("In publishing and graphic design, Lorem ipsum is a placeholder text commonly usd.")
                                .foregroundColor(.gray)
                        }
                        
                        Spacer()

                    }
                }
                
                
                HStack {
                    Image("LuffyProfilePicture")
                        .resizable()
                        .frame(width: 50, height: 40, alignment: .top)
                        .clipShape(Circle())
                        .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 0)
                        .padding(.leading, 16)
                    
                    TextField("Comment", text: $comment)
                        .padding(.all, 8)
                        .padding(.horizontal, 45)
                        .background(Color("WhiteGreyColor"))
                        .foregroundColor(Color("BlackWhiteColor"))
                        .frame(width: .infinity,
                               height: 42,
                               alignment: .leading)
                        .cornerRadius(32)
                        .shadow(radius: 4)
                        .padding(.trailing, 16)
                        .overlay(
                            HStack{
                                Image(systemName: "bubble.right")
                                    .padding(.leading, 16)
                                    .foregroundColor(Color("ShadowColor"))
                                Spacer()
                            }
                        )
                }
                .padding(.top)
            }
        }
        .navigationTitle("Post Title")
    }
    
    func getPostDetails() {
//        print("Inside Get Post List Function")
//
//        let access = UserDefaults.standard.string(forKey: "access")
//        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
//
//        let GET_POST_LIST = "http://20.212.216.183/api/classroom/\(classPk)/timeline/"
//
//        AF.request(GET_POST_LIST, method: .get, headers: headers).responseJSON { response in
//            guard let data = response.data else { return }
//            let status = response.response?.statusCode
//
//            if let response = try? JSONDecoder().decode([PostList].self, from: data) {
//                print("Get Post List Creator Success Status Code : \(String(describing: status))")
//                debugPrint("Create Classrooom Response Data Decoded")
//                postList = response
//                print(postList)
//                return
//            }
//            else {
//                print("Get Post List Creator Fail Status Code : \(String(describing: status))")
//                return
//            }
//        }
    }
}

struct DetailedPostView_Previews: PreviewProvider {
    static var previews: some View {
        DetailedPostView(postPk: 4, postTitle: "Title", postDescription: "Description", postDate: "Date", postType: "Module")
    }
}
