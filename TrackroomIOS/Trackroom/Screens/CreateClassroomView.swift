//
//  CreateClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI
import Alamofire

struct CreateClassroomView: View {
    @Binding var isActive: Bool
    
    @State var className: String = ""
    @State var classTypeSelection: String = "Public"
    @State var classDescription: String = ""
    @State var classCatagorySelection: String = "Calculus"
    
    var classType: [String] = ["Public", "Private"]
    var classCatagory: [String] = ["Calculus", "Quantam Physics", "English Litrature", "Machine Learning", "Cooking", "Web Development", "Others"]


    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack {
                Text("Create A class")
                    .font(.title2)
                    .fontWeight(.bold)
                    .padding()
                
                TextField("Class Name", text: $className)
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
                    .overlay(
                        HStack{
                            Image(systemName: "book.fill")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                    .padding(.bottom, 8)
                
                TextField("Class Description", text: $classDescription)
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
                    .overlay(
                        HStack{
                            Image(systemName: "line.3.horizontal")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                    .padding(.bottom, 8)

                HStack {
                    Text("Class Type")
                        .fontWeight(.bold)
                    Spacer()
                    Picker(selection: $classTypeSelection,
                           content: {
                        ForEach(classType, id: \.self) {result in
                            Text(result)
                                .foregroundColor(Color.white)
                                .fontWeight(.bold)
                        }
                    }, label: {
                        HStack {
                            Text(classTypeSelection)
                        }
                    })
                        .frame(width: 75, height: 30)
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 32)
                        .background(Color("PickerColor"))
                        .cornerRadius(10)
                        
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 8)
                
                HStack {
                    Text("Class Caragory")
                        .fontWeight(.bold)
                    Spacer()
                    Picker(selection: $classCatagorySelection,
                           content: {
                        ForEach(classCatagory, id: \.self) {result in
                            Text(result)
                        }
                    }, label: {
                        HStack {
                            Text(classCatagorySelection)
                        }
                    })
                        .frame(width: 75, height: 30, alignment: .center)
                        .foregroundColor(Color.white)
                        .padding(.horizontal, 32)
                        .background(Color("PickerColor"))
                        .cornerRadius(10)
                        
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 8)
                
                Text("Submit")
                    .font(.title3)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding(.top, 16)
                    .onTapGesture {
                        print("Inside Create Classroom Button On Tap Gesture")
                        let createClassroom = CreateClassroom(title: className,
                                                              description: classDescription,
                                                              class_type: classTypeSelection,
                                                              class_category: classCatagorySelection)
            
                        print("Create Classroom Request Data : \(createClassroom)")
                        
                        let access = UserDefaults.standard.string(forKey: "access")
                        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
                        
                        AF.request(CLASSROOM,
                                   method: .post,
                                   parameters: createClassroom,
                                   encoder: JSONParameterEncoder.default,
                                   headers: header).response { response in
                            
                            let status = response.response?.statusCode
                            print("Create Classroom Respoonse : \(String(describing: status))")
                            
                            switch response.result{
                                case .success:
                                    isActive.toggle()
                                case .failure(let error):
                                    print(error)
                            }
                        }
                    }
                
            }
            .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 500, idealHeight: .infinity, maxHeight: .infinity, alignment: .top)
            
        }
    }
}

//struct CreateClassroomView_Previews: PreviewProvider {
//    static var previews: some View {
//        CreateClassroomView()
//    }
//}
