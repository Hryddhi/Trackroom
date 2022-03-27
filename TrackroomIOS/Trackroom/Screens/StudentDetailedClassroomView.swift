//
//  StudentDetailedClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 27/3/22.
//

import SwiftUI

struct StudentDetailedClassroomView: View {
    @State var className: String = "Default Classroom"
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
                                    .padding(.trailing, 16)
                                    .frame(width: 50, height: 30, alignment: .leading)
                                    .onTapGesture {
                                        print("On Tab Gesture Leave Class")
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
                    .background(Color("GreyColor"))
                    .cornerRadius(10)
                    .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 3)
                    .padding(.horizontal)
                    
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
}

struct StudentDetailedClassroomView_Previews: PreviewProvider {
    static var previews: some View {
        StudentDetailedClassroomView()
    }
}
