//
//  CustomTextField.swift
//  Trackroom
//
//  Created by Rifatul Islam on 23/2/22.
//

import SwiftUI

struct CustomTextField: View {
    @State public var textFieldInput : String
    public var textFieldLabel : String
    
    var body: some View {
        TextField(textFieldLabel, text: $textFieldInput)
            .padding(.all, 32)
            .background(Color("WhiteGreyColor"))
            .foregroundColor(Color("BlackWhiteColor"))
            .frame(width: .infinity,
                   height: 50,
                   alignment: .leading)
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.horizontal, 16)
    }
}

//struct CustomTextField_Previews: PreviewProvider {
//    static var previews: some View {
//        CustomTextField()
//    }
//}
