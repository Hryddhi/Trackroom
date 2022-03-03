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
            
            TabView{
                TabHome()
                    .tabItem() {
                        Image(systemName: "house.fill")
                        Text("Home")
                }
                TabAssignment()
                    .tabItem() {
                        Image(systemName: "list.bullet.rectangle.fill")
                        Text("Assignments")
                }
                TabProfile()
                    .tabItem() {
                        Image(systemName: "person.fill")
                        Text("Profile")
                }
            }
            .accentColor(Color("PrimaryColor"))
        }
        .ignoresSafeArea()
        .navigationBarTitle("Trackroom") //this must be empty
//        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
    }
}

struct HomeViewController_Previews: PreviewProvider {
    static var previews: some View {
        HomeViewController()
    }
}


