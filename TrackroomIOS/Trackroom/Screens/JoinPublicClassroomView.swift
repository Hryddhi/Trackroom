//
//  JoinPublicClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI

struct JoinPublicClassroomView: View {
    @State var searchText: String = ""
    var body: some View {
        Text("Search for Courses")
            .searchable(text: $searchText, prompt: "Look for something")
            .navigationTitle("Searchable Example")
    }
}

struct JoinPublicClassroomView_Previews: PreviewProvider {
    static var previews: some View {
        JoinPublicClassroomView()
    }
}
