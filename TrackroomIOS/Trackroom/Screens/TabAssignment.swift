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

struct TabAssignment: View {
    
    @State private var result = [Result]()

    var body: some View {
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
        TabAssignment()
    }
}
