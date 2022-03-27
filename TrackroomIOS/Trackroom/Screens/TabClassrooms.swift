import SwiftUI
import Alamofire

struct TabClassrooms: View {
    @State var createdClassList: [ClassroomList] = []
    @State var privateClassroomList: [ClassroomList] = []
    @State var publicClassroomList: [ClassroomList] = []
    
    @State var isActiveJoinPublicClassroom: Bool = false
    @State var isActiveJoinPrivateClassroom: Bool = false
    @State var isActiveCreateClassroom: Bool = false


    
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            
            //Main Vertical Scroll View
            ScrollView {
                Text("Recommendations")
                    .fontWeight(.bold)
                    .padding(.leading)
                    .font(.title)
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                
                //Recommandations Horizontal Scroll View
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        RecommandationCard(imageName: "ClassIcon3")
                        RecommandationCard(imageName: "ClassIcon2")
                        RecommandationCard(imageName: "ClassIcon1")
                    }
                }
                .padding()
                
                //Created Class Title Section With Button
                HStack {
                    Text("Created Classes")
                        .fontWeight(.bold)
                        .padding(.leading)
                        .font(.title2)
                    
                    Spacer()
                    
                    CustomAddButton()
                        .padding(.trailing)
                        .sheet(isPresented: $isActiveCreateClassroom){
                            CreateClassroomView(isActive: $isActiveCreateClassroom)
                        } 
                        .onTapGesture {
                            isActiveCreateClassroom.toggle()
                        }
                    
                }
                .frame(minWidth: 350,
                       idealWidth: .infinity,
                       maxWidth: .infinity,
                       minHeight: 40,
                       idealHeight: 50,
                       maxHeight: 60,
                       alignment: .leading)
                
                
                //Created Classroom Horizontal Scroll View
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        if(createdClassList.count > 0) {
                            ForEach(createdClassList, id: \.self) { result in
                                NavigationLink(destination: CreatorDetailedClassroomView()) {
                                    ClassroomCard(classroomTitle: result.title, classroomType: result.class_type, classroomCatagory: result.class_category, classroomCreator: result.creator, imageName: "ClassIcon\(result.pk % 6)")
                               }
                            }
                        }
                        else {
                            Text ("Press the + button to create a class.")
                                .foregroundColor(Color("ShadowColor"))
                                .fontWeight(.bold)
                                .font(.caption)
                        }

                    }
                    .onAppear {
                        getCreatedClassroomList()
                    }
                }
                .padding()
                
                
                //Paid Cources Titile Section With Button
                HStack {
                    Text("Private Courses")
                        .fontWeight(.bold)
                        .padding(.leading)
                        .font(.title2)
                    
                    Spacer()
    
                    CustomAddButton()
                        .padding(.trailing)
                        .sheet(isPresented: $isActiveJoinPrivateClassroom){
                            JoinPrivateClassroomView(isActive: $isActiveJoinPrivateClassroom)
                        }
                        .onTapGesture {
                            isActiveJoinPrivateClassroom.toggle()
                        }
                }
                
                
                //Private Classroom Horizontal Scroll View
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        if(privateClassroomList.count > 0) {
                            ForEach(privateClassroomList, id: \.self) { result in
                                NavigationLink(destination: StudentDetailedClassroomView()) {
                                    ClassroomCard(classroomTitle: result.title, classroomType: result.class_type, classroomCatagory: result.class_category, classroomCreator: result.creator, imageName: "ClassIcon\(result.pk % 6)")
                                }
                            }
                        }
                        else {
                            Text ("Press the + button to join a private class.")
                                .foregroundColor(Color("ShadowColor"))
                                .fontWeight(.bold)
                                .font(.caption)
                        }
                        
                    }
                    .onAppear {
                        getPrivateClassroomList()
                    }
                }
                .padding()
                
                
                //Free Cources Titile Section With Button
                HStack {
                    Text("Public Courses")
                        .fontWeight(.bold)
                        .padding(.leading)
                        .font(.title2)
                    
                    Spacer()
                    
                    CustomAddButton()
                        .padding(.trailing)
                        .sheet(isPresented: $isActiveJoinPublicClassroom){
                            JoinPublicClassroomView()
                        }
                        .onTapGesture {
                            isActiveJoinPublicClassroom.toggle()
                        }
                    
                }
                
                
                
                //Free Cources Horizontal Scroll View
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        if(publicClassroomList.count > 0) {
                            ForEach(publicClassroomList, id: \.self) { result in
                                NavigationLink(destination: StudentDetailedClassroomView()) {
                                    ClassroomCard(classroomTitle: result.title, classroomType: result.class_type, classroomCatagory: result.class_category, classroomCreator: result.creator, imageName: "ClassIcon\(result.pk % 6)")
                                }
                            }
                        }
                        else {
                            Text ("Press the + button to join a public class.")
                                .foregroundColor(Color("ShadowColor"))
                                .fontWeight(.bold)
                                .font(.caption)
                        }
                    }
                }
                .padding()
                .onAppear {
                    getPublicClassroomList()
                }
            }
        }
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
        .padding(.top, 50)
        .ignoresSafeArea()
        .padding(.bottom,1)
    }
    
    func getCreatedClassroomList() {
        print("Inside Create Classroom List Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(CREATED_CLASSROOM_LIST, method: .get, headers: headers).responseJSON { response in
            guard let data = response.data else { return }
            let status = response.response?.statusCode
            print("Public Classroom List Response Data : \(data)")
            if let response = try? JSONDecoder().decode([ClassroomList].self, from: data) {
                print("Success Status Code : \(String(describing: status))")
                debugPrint("Create Classrooom Response Data Decoded")
                createdClassList = response
                print(createdClassList)
                return
            }
            else {
                print("Failed Status Code : \(String(describing: status))")
                return
            }
        }
    }
    
    func getPrivateClassroomList() {
        print("Inside Private Classroom List Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(PRIVATE_CLASSROOM_LIST, method: .get, headers: headers).responseJSON { response in
            guard let data = response.data else { return }
            print("Privare Classroom List Response Data : \(data)")
            let status = response.response?.statusCode
            if let response = try? JSONDecoder().decode([ClassroomList].self, from: data) {
                print("Success Status Code : \(String(describing: status))")
                print("Private Classroom List Response Data Decoded")
                privateClassroomList = response
                print(privateClassroomList)
                return
            }
            else {
                print("Failed Status Code : \(String(describing: status))")
                return
            }
        }
    }
    
    func getPublicClassroomList() {
        print("Inside Public ClassroomList Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        AF.request(PUBLIC_CLASSROOM_LIST, method: .get, headers: headers).responseJSON { response in
            let status = response.response?.statusCode
            guard let data = response.data else { return }
            print("Public Classroom List Response Data : \(data)")
            if let response = try? JSONDecoder().decode([ClassroomList].self, from: data) {
                print("Success Status Code : \(String(describing: status))")
                print("Response Data Decoded 3")
                publicClassroomList = response
                print(publicClassroomList)
                return
            }
            else {
                print("Success Status Code : \(String(describing: status))")
                return
            }
        }
    }
}

struct TabHome_Previews: PreviewProvider {
    static var previews: some View {
        TabClassrooms()
    }
}

struct RecommandationCard: View {
    public var imageName : String
    var body: some View {
        ZStack(alignment: .leading) {
            Image(imageName)
                .resizable()
                .frame(width: .infinity,
                       height: 200,
                       alignment: .center)
                .blendMode(.screen)
                .opacity(0.5)

            VStack(alignment: .leading, spacing: 16){

                HStack {
                    Text("Classroom 1")
                        .font(.title2)
                        .fontWeight(.bold)

                    Spacer()

                    Image(systemName: "plus.app.fill")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .foregroundColor(Color("PrimaryColor"))
                }

                Text("4.5 ☆ • Cooking")
                    .fontWeight(.bold)

                Text("This is a sample classroom 1 where we lrean to cook")
                    .frame(width: .infinity, height: 30, alignment: .leading)

                Text("Isntructor Name")
                    .font(.caption)
                    .fontWeight(.bold)
            }
        }
        .padding(.all, 16)
        .frame(minWidth: 280,
               idealWidth: 300,
               maxWidth: 320,
               minHeight: 170,
               idealHeight: 180,
               maxHeight: 200,
               alignment: .leading)
        .background(Color("ClassroomCardBgColor"))
        .cornerRadius(10)
        .shadow(radius: 3)
        .foregroundColor(Color("BlackWhiteColor"))
    }
}
