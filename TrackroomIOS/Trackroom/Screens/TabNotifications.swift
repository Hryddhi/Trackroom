//
//  TabAssignment.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//
import SwiftUI

struct Response: Codable {
    var results: [Result]
}

struct Result: Codable {
    var trackId: Int
    var trackName: String
    var collectionName: String
}

struct TabNotifications: View {
    
    @State private var result = [Result]()

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
                
                List(result, id: \.trackId) { item in
                    VStack(alignment: .leading) {
                        Text(item.trackName)
                            .font(.headline)
                        Text(item.collectionName)
                    }
                }
                .task {
                    await loadData()
                }

            }
        }
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
}

struct TabAssignment_Previews: PreviewProvider {
    static var previews: some View {
        TabNotifications()
    }
}

struct notificationCard: View {
    var body: some View {
        VStack {
                Text("Classroom 213")
                    .font(.title2)
                    .fontWeight(.bold)
                    .frame(minWidth: 150, idealWidth: .infinity, maxWidth: .infinity, minHeight: 12, idealHeight: 16, maxHeight: 20, alignment: .leading)
                Text("New Material has been posted by your teacher in classsroom 321 with a fixed deasline")
                    .frame(minWidth: 150, idealWidth: .infinity, maxWidth: .infinity, minHeight: 12, idealHeight: 50, maxHeight: 100, alignment: .leading)
                Text("12-12-2021")
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
