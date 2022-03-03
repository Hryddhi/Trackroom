//
//  TabHome.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI

struct TabHome: View {
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                Text("Recommendations")
                    .fontWeight(.bold)
                    .padding(.leading)
                    .font(.title)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        RecommandationCard(imageName: "ClassIcon1")
                        RecommandationCard(imageName: "ClassIcon2")
                        RecommandationCard(imageName: "ClassIcon3")
                        RecommandationCard(imageName: "ClassIcon4")
                        RecommandationCard(imageName: "ClassIcon5")
                    }
                }
                .padding()
                
                Text("Created Classes")
                    .fontWeight(.bold)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                    .padding(.leading)
                    .font(.title2)
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        UserCard(imageName: "ClassIcon6")
                        UserCard(imageName: "ClassIcon5")
                        UserCard(imageName: "ClassIcon4")
                        UserCard(imageName: "ClassIcon3")
                        UserCard(imageName: "ClassIcon2")
                    }
                }
                .padding()
                
                Text("Enrolled Classes")
                    .fontWeight(.bold)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                    .padding(.leading)
                    .font(.title2)
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        UserCard(imageName: "ClassIcon1")
                        UserCard(imageName: "ClassIcon2")
                        UserCard(imageName: "ClassIcon3")
                        UserCard(imageName: "ClassIcon4")
                        UserCard(imageName: "ClassIcon5")
                    }
                }
                .padding()
            }
        }
        .navigationBarHidden(true)
    }
}

struct TabHome_Previews: PreviewProvider {
    static var previews: some View {
        TabHome()
    }
}

struct RecommandationCard: View {
    public var imageName : String
    var body: some View {
        VStack(alignment: .leading, spacing: 16){
            Image(imageName)
                .resizable()
                .scaledToFit()
                .frame(width: 150, height: 150, alignment: .leading)
            Text("Classroom 1")
                .font(.title2)
                .fontWeight(.bold)
            Text("This is a sample classroom one for all students of this class")
            Text("Isntructor Name")
                .font(.caption)
        }
        .padding(.all, 16)
        .frame(minWidth: 280, idealWidth: 300, maxWidth: 320, minHeight: 280, idealHeight: 300, maxHeight: 320, alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
    }
}

struct UserCard: View {
    public var imageName : String
    var body: some View {
        VStack(alignment: .leading, spacing: 8){
            Text("Classroom 1")
                .font(.title2)
                .fontWeight(.bold)
            HStack {
                Text("This is a sample classroom one for all students of this class")
                Image(imageName)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 100, height: 100, alignment: .trailing)
            }
            Text("Isntructor Name")
                .font(.caption)
        }
        .padding(.all, 16)
        .frame(minWidth: 280,
               idealWidth: 300,
               maxWidth: 320,
               minHeight: 200,
               idealHeight: 210,
               maxHeight: 220,
               alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
    }
    
}
