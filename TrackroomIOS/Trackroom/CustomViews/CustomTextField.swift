//
//  SwiftUIView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 14/4/22.
//

import SwiftUI

struct CustomTextField: View {
    var textFieldLabel : String
    @Binding public var textFieldInput : String
    var iconName: String
    
    var body: some View {
        TextField(textFieldLabel, text: $textFieldInput)
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
            .textInputAutocapitalization(.never)
            .keyboardType(.emailAddress)
            .disableAutocorrection(true)
            .overlay(
                HStack{
                    Image(systemName: iconName)
                        .padding(.horizontal, 32)
                        .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                        .foregroundColor(Color("BlackWhiteColor"))
                }
            )
    }
}

//struct SwiftUIView_Previews: PreviewProvider {
//    static var previews: some View {
//        CustomTextField(textFieldLabel: <#String#>, textFieldInput: <#Binding<String>#>, iconName: <#String#>)
//    }
//}
