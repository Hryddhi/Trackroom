//
//  TabProfile.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI
import Alamofire

//struct Response2: Codable {
//    var results: [Result2]
//}

struct Result2: Codable {
    var id: Int
    var title: String
    var price: Float
    var category: String
    var description: String
    var image: String
}
struct TabProfile: View {
    @State var result2 = [Result2]()
    var body: some View {
        
        List(result2, id: \.id) { item in
            VStack(alignment: .leading) {
                Text(item.title)
                    .font(.headline)
                Text("\(item.price, specifier: "%.2f")")
            }
        }
        .onAppear(perform: fetchFilms)
    }
    
    func fetchFilms() {
        //        let request = AF.request("https://jsonplaceholder.typicode.com/posts/")
        //        request.responseJSON { (data) in
        //            print(data)
        
        print("inside fetch function")
        
        AF.request("https://fakestoreapi.com/products/1").responseJSON { response in
            debugPrint("request made")
            guard let data = response.data else { return }
            debugPrint("request data save")
            if let response = try? JSONDecoder().decode(Result2.self, from: data) {
                debugPrint("decoded data")
                DispatchQueue.main.async {
                    let title = response.title
                    let price = response.price
                    print(title)
                    debugPrint("viewing data")
                }
                return
            }
            else {
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
