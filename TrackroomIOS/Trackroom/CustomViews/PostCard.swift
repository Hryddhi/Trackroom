//
//  PostCard.swift
//  Trackroom
//
//  Created by Rifatul Islam on 27/3/22.
//

import SwiftUI

struct PostCard: View {
    var body: some View {
        VStack {
            Text("Post Title")
                .foregroundColor(Color("BlackWhiteColor"))
                .font(.callout)
                .fontWeight(.bold)
                .padding(.horizontal)
                .frame(minWidth: 200, idealWidth: .infinity, maxWidth: .infinity, minHeight: 10, idealHeight: 15, maxHeight: 20, alignment: .leading)
            
            Text("Date Created")
                .foregroundColor(Color("BlackWhiteColor"))
                .font(.footnote)
                .fontWeight(.bold)
                .padding(.horizontal)
                .frame(minWidth: 200, idealWidth: .infinity, maxWidth: .infinity, minHeight: 10, idealHeight: 15, maxHeight: 20, alignment: .leading)
            
            Text("Learn how to use fonts in SwiftUI and customize Text view in SwiftUI with number of Font options such as design, size, weight, color and others.")
                .foregroundColor(Color("BlackWhiteColor"))
                .font(.callout)
                .padding(.horizontal)
                .frame(minWidth: 200, idealWidth: .infinity, maxWidth: .infinity, minHeight: 30, idealHeight: 40, maxHeight: 50, alignment: .leading)
        }
        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 120, idealHeight: 150, maxHeight: 200, alignment: .center)
        .background(Color("LightGreyColor"))
        .cornerRadius(10)
        .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 3)
        .padding(.horizontal)
    }
}

struct PostCard_Previews: PreviewProvider {
    static var previews: some View {
        PostCard()
    }
}
