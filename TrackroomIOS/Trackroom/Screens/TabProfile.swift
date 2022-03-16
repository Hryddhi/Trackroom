import SwiftUI
import Alamofire

struct TabProfile: View {
    @State var editProfile = false
    @State var fullName: String = ""
    @State var email: String = ""
    
    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            VStack{
                Image("LuffyProfilePicture")
                    .resizable()
                    .frame(width: 170, height: 150, alignment: .top)
                    .clipShape(Circle())
                    .padding(.bottom)
            }
            .frame(minWidth: 360,
                   idealWidth: .infinity,
                   maxWidth: .infinity,
                   minHeight: 200,
                   idealHeight: 220,
                   maxHeight: 240,
                   alignment: .center)
            .background(Color("SecondaryColor"))


            VStack(alignment: .leading, spacing: 16){
                Text("Full Name : \(fullName)")
                    .font(.title3)
                    .fontWeight(.bold)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .leading)
                    .padding(.top)
                    .padding(.leading)
                Text("Email Address : \(email)")
                    .font(.title3)
                    .fontWeight(.bold)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .leading)
                    .padding(.leading)
                Text("University : NSU")
                    .font(.title3)
                    .fontWeight(.bold)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .leading)
                    .padding(.leading)
                Text("Password : *****")
                    .font(.title3)
                    .fontWeight(.bold)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .leading)
                    .padding(.leading)
                Text("Social Media Link : FB, Twitter")
                    .font(.title3)
                    .fontWeight(.bold)
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .leading)
                    .padding(.leading)
                Text("Edit Profile")
                    .font(.footnote)
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .frame(minWidth: 300, idealWidth: .infinity, maxWidth: .infinity, minHeight: 40, idealHeight: 50, maxHeight: 60, alignment: .center)
                    .padding(.leading)
                    .onTapGesture {
                        editProfile.toggle()
                    }
                    .sheet(isPresented: $editProfile, content: {
                                Text("Profile Editing Page")
                            })            }
            .padding(.top, 250)
            .frame(minWidth: 360,
                   idealWidth: .infinity,
                   maxWidth: .infinity,
                   minHeight: 180,
                   idealHeight: 230,
                   maxHeight: 250,
                   alignment: .top)
            
        }
        .navigationTitle("Settings")
        //.onAppear(perform: getUserInfo)
    }
    
    
    
    func getUserInfo() {
        print("Inside getUserInfo Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        print("Auth Header : \(headers)")
        AF.request(USER_INFO_URL, method: .get, headers: headers).responseJSON { response in
            print("Request Made")
            guard let data = response.data else { return }
            print("Request Data Save")
            if let response = try? JSONDecoder().decode(getUserInfoResponse.self, from: data) {
                debugPrint("decoded data")
                DispatchQueue.main.async {
                    fullName = response.username
                    email = response.email
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
}

struct TabProfile_Previews: PreviewProvider {
    static var previews: some View {
        TabProfile()
    }
}
