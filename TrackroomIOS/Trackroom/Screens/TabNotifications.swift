//
//  TabAssignment.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//
import SwiftUI
import Alamofire

struct Response: Codable {
    var results: [NotificationList]
}

struct Result: Codable {
    var trackId: Int
    var trackName: String
    var collectionName: String
}

struct TabNotifications: View {
    
    @State private var result = [Result]()
    @State var notificationListArray = [NotificationList]()

    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            
            ScrollView {
                Text("Notifications")
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
                
                ForEach(1..<15) { i in
                    notificationCard()
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                }
                .onAppear {
                    loadNotification()
                }
                
//                List(result, id: \.trackId) { item in
//                    VStack(alignment: .leading) {
//                        Text(item.trackName)
//                            .font(.headline)
//                        Text(item.collectionName)
//                    }
//                }
//                .task {
//                    await loadData()
//                }

            }
        }
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
        .padding(.vertical, 80)
        .ignoresSafeArea()
    }
    
    func loadData() async {
        guard let url = URL(string: "https://itunes.apple.com/search?term=taylor+swift&entity=song") else {
            print("Invalid URL")
            return
        }
            do {
                let (data, _) = try await URLSession.shared.data(from: url)
                print("connection establishes")

                if let decodedResponse = try? JSONDecoder().decode(Response.self, from: data) {
                    result = decodedResponse.results
                    //print(result)
                }
                else {
                    print("Failed to purse shits")
                }

            } catch {
                print("Invalid data")
            }
        }
    
    func loadNotification() {
        print("Inside Load Notification Function")
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        print("Auth Header : \(headers)")
        AF.request(USER_INFO_URL, method: .get, headers: headers).responseJSON { response in
            print("Load Notification Request Sucessfull")
            guard let data = response.data else { return }
            print("Login Function Request Saved to Data")
            if let response = try? JSONDecoder().decode([NotificationList].self, from: data) {
                debugPrint("Login Request Response Data Decoded")
                let notificationList = response
                print("received access  : \(notificationList.classroom) ")
                print("received refresh  : \(notificationList.message) ")
                print("received refresh  : \(notificationList.date) ")
                return
            }
            else {
                let status = response.response?.statusCode
                print("Status Code : \(status)")
                print("Failed to save request")
                return
            }
        }
    }
}

struct TabAssignment_Previews: PreviewProvider {
    static var previews: some View {
        TabNotifications()
    }
}

struct notificationCard: View {
    @State var classroom: String
    @State var message: String
    @State var date: String
    var body: some View {
        VStack {
                Text(classroom)
                    .font(.title2)
                    .fontWeight(.bold)
                    .frame(minWidth: 150, idealWidth: .infinity, maxWidth: .infinity, minHeight: 12, idealHeight: 16, maxHeight: 20, alignment: .leading)
                Text(message)
                    .frame(minWidth: 150, idealWidth: .infinity, maxWidth: .infinity, minHeight: 12, idealHeight: 50, maxHeight: 100, alignment: .leading)
                Text(date)
                    .font(.footnote)
                    .fontWeight(.bold)
                    .frame(minWidth: 150, idealWidth: .infinity, maxWidth: .infinity, minHeight: 12, idealHeight: 16, maxHeight: 20, alignment: .leading)
        }
        .frame(minWidth: 200, idealWidth: .infinity, maxWidth: .infinity, minHeight: 100, idealHeight: 110, maxHeight: 120, alignment: .leading)
        .padding(.all, 16)
        .background(Color("WhiteGreyColor"))
        .cornerRadius(10)
        .shadow(color: Color("ShadowColor"), radius: 5, x: 0, y: 0)
        
    }
}
