//
//  JoinPublicClassroomView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 20/3/22.
//

import SwiftUI
import Alamofire

struct JoinPublicClassroomView: View {
    @State var getClassroomList: [ClassroomList] = []
    @State var searchText: String = ""
    @State var isPublicClassroomCardVisible: Bool = false
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack {
                Text("Enroll To A Public Course")
                    .font(.title2)
                    .fontWeight(.bold)
                    .padding()
                TextField("Search Here...", text: $searchText)
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
                    .onSubmit {
                        print("Search text has been submitted.. \(searchText)")
                        isPublicClassroomCardVisible = true
                        getPublicClassroomList()
                    }
                    .overlay(
                        HStack{
                            Image(systemName: "magnifyingglass")
                                .padding(.horizontal, 32)
                                .frame(minWidth: 290, idealWidth: .infinity, maxWidth: .infinity, minHeight: 50, idealHeight: 50, maxHeight: 50, alignment: .leading)
                                .foregroundColor(Color("ShadowColor"))
                        }
                    )
                
                if(isPublicClassroomCardVisible) {
                    Text ("All Avilable Classrooms")
                        .fontWeight(.bold)
                        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 30, idealHeight: 40, maxHeight: 50, alignment: .leading)
                        .padding(.horizontal, 32)
                    ScrollView(showsIndicators: false) {
                        ForEach(getClassroomList, id: \.self) { result in
                            PublicClassroomCard(classPK: result.pk, className: result.title, classCreator: result.creator, classDescription: result.description, classRating: result.ratings, classCatagory: result.class_category, imageName: "ClassIcon\(result.pk % 6)")
                                .padding(.vertical, 4)
                        }
                    }
                    .padding(.top, 4)
                }
            }
            .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 500, idealHeight: .infinity, maxHeight: .infinity, alignment: .top)
            
        }
    }
    
    func getPublicClassroomList() {
        print("Inside Join Public Classroom List Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(CLASSROOM,
                   method: .get,
                   headers: header).responseJSON { response in
            let status = response.response?.statusCode
            guard let data = response.data else { return }
            print(data)
            if let response = try? JSONDecoder().decode([ClassroomList].self, from: data) {
                debugPrint("Response Data Decoded 1")
                getClassroomList = response
                //print(getClassroomList)
                print("Status Code : \(status)")
                return
            }
            else {
                print("Status Code : \(status)")
                return
            }
        }
    }
}

struct JoinPublicClassroomView_Previews: PreviewProvider {
    static var previews: some View {
        JoinPublicClassroomView()
    }
}

struct PublicClassroomCard: View {
    public var classPK : Int
    public var className : String
    public var classCreator: String
    public var classDescription: String
    public var classRating: String
    public var classCatagory: String
    public var imageName : String
    @State var classJoinSuvessfull: Bool = false

    var body: some View {
        ZStack(alignment: .leading) {
            Image(imageName)
                .resizable()
                .frame(width: .infinity,
                       height: 200,
                       alignment: .center)
                .blendMode(.screen)

            VStack(alignment: .leading, spacing: 8){

                HStack {
                    Text(className)
                        .font(.title2)
                        .fontWeight(.bold)

                    Spacer()

                    Image(systemName: "plus.app.fill")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .foregroundColor(Color("PrimaryColor"))
                        .onTapGesture {
                            print("Classroom Will be added")
                            joinPublicClassroom()
                        }
                        .alert(isPresented: $classJoinSuvessfull) {
                            Alert(title: Text("Successfull"), message: Text("Class had been jonied sucessfully."), dismissButton: .default(Text("OK")))
                        }
                }

                Text("\(classRating) ☆ • \(classCatagory)")
                    .fontWeight(.bold)

                Text(classDescription)
                    .frame(width: .infinity, height: 30, alignment: .leading)

                Text(classCreator)
                    .font(.caption)
                    .fontWeight(.bold)
            }
        }
        .padding(.all, 16)
        .frame(minWidth: 280,
               idealWidth: 300,
               maxWidth: 320,
               minHeight: 130,
               idealHeight: 150,
               maxHeight: 180,
               alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
        .shadow(radius: 3)
        .foregroundColor(Color("BlackWhiteColor"))
    }
    
    func joinPublicClassroom() {
        let JOIN_PUBLIC_CLASSROOM = "http://20.212.216.183/api/classroom/\(classPK)/join/"
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(JOIN_PUBLIC_CLASSROOM,
                   method: .post,
                   headers: header).responseJSON { response in
            let status = response.response?.statusCode
            print("Status Code \(String(describing: status))")
            switch response.result{
            case .success:
                if(status == 201) {
                    print("Classroom \(className) has been joined sucessfully")
                    classJoinSuvessfull = true
                }
                else {
                    classJoinSuvessfull = false
                }
            case .failure(let error):
                print(error)
            }
        }
    }
}
