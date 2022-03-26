//
//  DetailedClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI

struct CreatorDetailedClassroomView: View {
    @State var className: String = "Default Classroom"
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ScrollView {
                ZStack {
                    Image("ClassIcon5")
                        .resizable()
                        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 300, idealHeight: 350, maxHeight: 400, alignment: .center)
                        .blendMode(.screen)
                        .edgesIgnoringSafeArea(.all)
                        .opacity(0.5)
                }
                .background(Color("SecondaryColor"))
            }
        }
        .navigationTitle("Classroom Name")
    }
}

struct DetailedClassroomView_Previews: PreviewProvider {
    static var previews: some View {
        CreatorDetailedClassroomView()
    }
}
