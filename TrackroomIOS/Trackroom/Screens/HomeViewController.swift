//
//  HomeViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI

struct HomeViewController: View {
    var body: some View {
        ZStack{
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
                        RecommandationCard()
                        RecommandationCard()
                        RecommandationCard()
                        RecommandationCard()
                        RecommandationCard()
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
                        UserCard()
                        UserCard()
                        UserCard()
                        UserCard()
                        UserCard()
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
                        UserCard()
                        UserCard()
                        UserCard()
                        UserCard()
                        UserCard()
                    }
                }
                .padding()
                
            }
        }
    }
}

struct HomeViewController_Previews: PreviewProvider {
    static var previews: some View {
        HomeViewController()
    }
}

struct RecommandationCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16){
            Image("ClassIconMaths")
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
        .frame(minWidth: 280, idealWidth: 200, maxWidth: 320, minHeight: 280, idealHeight: 300, maxHeight: 320, alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
    }
}

struct UserCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16){
            Image("ClassIconMaths")
                .resizable()
                .scaledToFit()
                .frame(width: 100, height: 100, alignment: .leading)
            Text("Classroom 1")
                .font(.title2)
                .fontWeight(.bold)
            Text("This is a sample classroom one for all students of this class")
            Text("Isntructor Name")
                .font(.caption)
        }
        .padding(.all, 16)
        .frame(minWidth: 220, idealWidth: 240, maxWidth: 290, minHeight: 180, idealHeight: 200, maxHeight: 220, alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
    }
}
