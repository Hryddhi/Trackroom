//
//  CreatorDetailedPostView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 27/3/22.
//

import SwiftUI

struct CreatorCreateNewPostView: View {
    @State var postTitle: String = ""
    @State var postDescription: String = ""
    @State var postTypeSelection: String = "Text"
    
    var postType: [String] = ["Text" , "Document", "Image"]
    
    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack{
                Text("Create A New Post")
                    .fontWeight(.bold)
                    .font(.title3)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 30,
                           idealHeight: 40,
                           maxHeight: 50,
                           alignment: .center)
                    .padding(.top, 32)
                
                TextField("Post Title", text: $postTitle)
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
                    .overlay(
                        HStack{
                            Image(systemName: "character.bubble")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                TextField("Post Description", text: $postDescription)
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
                    .overlay(
                        HStack{
                            Image(systemName: "text.bubble")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                HStack {
                    Text("Post Type")
                        .fontWeight(.bold)
                    
                    Spacer()
                    
                    Picker(selection: $postTypeSelection,
                           content: {
                        ForEach(postType, id: \.self) {result in
                            Text(result)
                                .foregroundColor(Color.white)
                                .fontWeight(.bold)
                        }
                    }, label: {
                        HStack {
                            Text(postTypeSelection)
                        }
                    })
                        .frame(width: 75, height: 30)
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 32)
                        .background(Color("GreyColor"))
                        .cornerRadius(10)
                        
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 8)
                
                Text("Submit")
                    .font(.body)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding()
            }
        }
    }
}

struct CreatorDetailedPostView_Previews: PreviewProvider {
    static var previews: some View {
        CreatorCreateNewPostView()
    }
}
