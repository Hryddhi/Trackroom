import SwiftUI
import Alamofire

struct TabProfile: View {
    //@Environment(\.presentationMode) var presentationMode
    @State var logoutSuccess: Bool = false
    @State var isActive: Bool = false
    @State var isActive2: Bool = false
    @State var fullName: String = ""
    @State var email: String = ""
    @State var userBio: String = ""
    
    var body: some View {
        ScrollView {
            ZStack(alignment: .top){
                Color("BgColor")
                    .edgesIgnoringSafeArea(.all)
                VStack{
                    HStack {
                        Text("Profile")
                            .fontWeight(.bold)
                            .padding(.leading)
                            .font(.title)
                            
                        Spacer()

//                        NavigationLink(destination: WelcomeViewController(), isActive: $logoutSuccess) {
//                            Text("Logout")
//                                .fontWeight(.bold)
//                                .foregroundColor(Color("PrimaryColor"))
//                                .padding(.trailing, 16)
//                                .onTapGesture {
//                                    logoutUser()
//                                    //self.presentationMode.wrappedValue.dismiss()
//                                }
//                        }
                        
                    }
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)

                    
                    
                    Image("LuffyProfilePicture")
                        .resizable()
                        .frame(width: 170, height: 150, alignment: .top)
                        .clipShape(Circle())
                        .padding(.bottom)
                    
                    CustomDivider()
                }
                .frame(minWidth: 300,
                       idealWidth: .infinity,
                       maxWidth: .infinity,
                       minHeight: 240,
                       idealHeight: 250,
                       maxHeight: 260,
                       alignment: .center)

                VStack(alignment: .leading, spacing: 16){
                    
                    
                    HStack {
                        Image(systemName: "person.fill")
                        
                        Text("Full Name : \(fullName)")
                            .font(.body)
                            .fontWeight(.bold)
                        
//                        Spacer()
//
//                        Image(systemName: "square.and.pencil")
//                            .padding(.trailing, 32)
//                            .foregroundColor(Color("PrimaryColor"))
//                            .onTapGesture {
//                                isActive.toggle()
//                            }
//                            .sheet(isPresented: $isActive){
//                                EditProfileView(showModal: $isActive)
//                            }
                            
                    }
                    .padding(.trailing)
                    .frame(minWidth: 300,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                    .padding(.leading, 32)
                    
                    HStack {
                        Image(systemName: "envelope.fill")

                        Text("Email Address : \(email)")
                            .font(.body)
                            .fontWeight(.bold)
                    }
                    .padding(.trailing)
                    .frame(minWidth: 300,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                    .padding(.leading, 32)
                    
                    HStack {
                        Image(systemName: "lock.fill")

                        Text("Password : ••••••••")
                            .font(.body)
                            .fontWeight(.bold)
                    }
                    .frame(minWidth: 300,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .leading)
                    .padding(.leading, 32)
                    
                    HStack {
                        Image(systemName: "bookmark.fill")
                            .frame(width: 20, height: 45, alignment: .top)

                        Text("Bio : \(userBio)")
                            .font(.body)
                            .fontWeight(.bold)
                            .frame(width: 300, height: 50, alignment: .topLeading)
                    }
                    .frame(minWidth: 300,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 50,
                           idealHeight: 60,
                           maxHeight: 70,
                           alignment: .leading)
                    .padding(.top, 10)
                    .padding(.trailing)
                    .padding(.leading, 32)
                    .padding(.bottom, 32)
                    
//                    Text("Edit Profile")
//                        .font(.subheadline)
//                        .fontWeight(.bold)
//                        .foregroundColor(Color("PrimaryColor"))
//                        .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .center)
//                        .padding(.top)
//                        .onTapGesture {
//                            isActive.toggle()
//                        }
//                        .sheet(isPresented: $isActive){
//                            EditProfileView(showModal: $isActive)
//                        }

                    HStack {
                        Text("Edit Profile")
                            .font(.subheadline)
                            .fontWeight(.bold)
                            .foregroundColor(Color("PrimaryColor"))
                            .padding(.leading,45)
                            .onTapGesture {
                                isActive.toggle()
                            }
                            .sheet(isPresented: $isActive){
                                EditProfileView(showModal: $isActive)
                        }
                        Spacer()
                        
                        Text("Edit Password")
                            .font(.subheadline)
                            .fontWeight(.bold)
                            .foregroundColor(Color("PrimaryColor"))
                            .padding(.trailing, 45)
                            .onTapGesture {
                                isActive2.toggle()
                            }
                            .sheet(isPresented: $isActive2){
                                EditPasswordView(showModal: $isActive2)
                            }
                    }
                    
                    NavigationLink(destination: WelcomeViewController(), isActive: $logoutSuccess) {
                        Text("Logout")
                            .fontWeight(.bold)
                            .foregroundColor(Color("PrimaryColor"))
                            .padding(.trailing, 16)
                            .onTapGesture {
                                logoutUser()
                                //self.presentationMode.wrappedValue.dismiss()
                            }
                    }
                    .frame(minWidth: 350,
                           idealWidth: .infinity,
                           maxWidth: .infinity,
                           minHeight: 40,
                           idealHeight: 50,
                           maxHeight: 60,
                           alignment: .center)
                    
                }
                .padding(.top,250)
                .frame(minWidth: 360,
                       idealWidth: .infinity,
                       maxWidth: .infinity,
                       minHeight: 180,
                       idealHeight: 230,
                       maxHeight: 250,
                       alignment: .top)
                
            }
        }
        .onAppear(perform: getUserInfo)
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
        .padding(.top, 50)
        .ignoresSafeArea()
        

        
    }
    
    
    
    func getUserInfo() {
        print("Inside getUserInfo Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let header: HTTPHeaders = [.authorization(bearerToken: access!)]
        print("Auth Header : \(header)")
        AF.request(USER_INFO_URL, method: .get, headers: header).responseJSON { response in
            print("Request Made")
            guard let data = response.data else { return }
            print("Request Data Save")
            if let response = try? JSONDecoder().decode(getUserInfoResponse.self, from: data) {
                debugPrint("decoded data")
                DispatchQueue.main.async {
                    fullName = response.username
                    email = response.email
                    userBio = response.bio ?? "\(fullName) is on trackroom now!"
                    print("Username : \(fullName)")
                    print("Email : \(email)")
                }
                return
            }
            else {
                let status = response.response?.statusCode
                print("Status Code : \(status)")
                print("Failed to send request")
            }
        }
    }
    
    func logoutUser() {
//        print("Inside LogoutUser Function")
//        AF.request(BLACKLIST_REFRESH,
//                   method: .post,
//                   parameters: ,
//                   encoder: JSONParameterEncoder.default).responseJSON { response in
//            print("Request Made")
//            let status = response.response?.statusCode
//            print("Status Code : \(status)")
//            switch response.result{
//                case .success:
//                    logoutSuccess = true
//                case .failure(let error):
//                    logoutSuccess = false
//            }
//        }
        
        print(UserDefaults.standard.string(forKey: "access"))
        UserDefaults.standard.removeObject(forKey: "access")
        print(UserDefaults.standard.string(forKey: "access"))
        logoutSuccess.toggle()

    }
}

struct TabProfile_Previews: PreviewProvider {
    static var previews: some View {
        TabProfile()
    }
}
