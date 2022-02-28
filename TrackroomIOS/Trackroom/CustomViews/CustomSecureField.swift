//
//  CustomSecureField.swift
//  Trackroom
//
//  Created by Rifatul Islam on 23/2/22.
//

import SwiftUI

struct CustomSecureField: View {
    
    @State public var secureFieldInput : String
    public var secureFieldLabel : String

    var body: some View {
        SecureField(secureFieldLabel, text: $secureFieldInput)
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

//struct CustomSecureField_Previews: PreviewProvider {
//    static var previews: some View {
//        CustomSecureField()
//    }
//}
