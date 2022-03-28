//
//  InviteStudentView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/3/22.
//

import SwiftUI

struct InviteStudentView: View {
    @State var inviteNumberSelection: String = "0"
    var inviteNumber: [String] = ["1","2","3","4","5"]
    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack{
                Text("Invite A Student")
                    .fontWeight(.bold)
                    .font(.title3)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 30,
                           idealHeight: 40,
                           maxHeight: 50,
                           alignment: .center)
                    .padding(.top, 32)
                
                HStack {
                    Text("Invite Student")
                        .fontWeight(.bold)
                    
                    Spacer()
                    
                    Picker(selection: $inviteNumberSelection,
                           content: {
                        ForEach(inviteNumber, id: \.self) {result in
                            Text(result)
                                .foregroundColor(Color.white)
                                .fontWeight(.bold)
                        }
                    }, label: {
                        HStack {
                            Text(inviteNumberSelection)
                        }
                    })
                        .frame(width: 75, height: 30)
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 32)
                        .background(Color("GreyColor"))
                        .cornerRadius(10)
                        
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 8)
            }
        }
    }
}

struct InviteStudentView_Previews: PreviewProvider {
    static var previews: some View {
        InviteStudentView()
    }
}
