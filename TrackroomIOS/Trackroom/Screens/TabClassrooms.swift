import SwiftUI

struct TabClassrooms: View {
    var body: some View {
        ZStack {
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
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
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        RecommandationCard(imageName: "ClassIcon1")
                        RecommandationCard(imageName: "ClassIcon2")
                        RecommandationCard(imageName: "ClassIcon3")
                        RecommandationCard(imageName: "ClassIcon2")
                        RecommandationCard(imageName: "ClassIcon1")
                    }
                }
                .padding()
                
                HStack {
                    Text("Created Classes")
                        .fontWeight(.bold)
                        .padding(.leading)
                    .font(.title2)
                    
                    Spacer()
                    
                    Text("+")
                        .font(.title3)
                        .fontWeight(.bold)
                        .frame(width: 60, height: 35, alignment: .center)
                        .foregroundColor(.white)
                        .background(Color("PrimaryColor"))
                        .cornerRadius(32)
                        .shadow(radius: 4)
                        .padding(.trailing)
                    
                }
                .frame(minWidth: 350,
                       idealWidth: .infinity,
                       maxWidth: .infinity,
                       minHeight: 40,
                       idealHeight: 50,
                       maxHeight: 60,
                       alignment: .leading)
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        ClassroomCard(imageName: "ClassIcon6")
                        ClassroomCard(imageName: "ClassIcon5")
                        ClassroomCard(imageName: "ClassIcon4")
                        ClassroomCard(imageName: "ClassIcon3")
                        ClassroomCard(imageName: "ClassIcon2")
                    }
                }
                .padding()
                
                HStack {
                    Text("Paid Courses")
                        .fontWeight(.bold)
                        .padding(.leading)
                        .font(.title2)
                    
                    Spacer()
                    
                    Text("+")
                        .font(.title3)
                        .fontWeight(.bold)
                        .frame(width: 60, height: 35, alignment: .center)
                        .foregroundColor(.white)
                        .background(Color("PrimaryColor"))
                        .cornerRadius(32)
                        .shadow(radius: 4)
                        .padding(.trailing)
                }
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        ClassroomCard(imageName: "ClassIcon1")
                        ClassroomCard(imageName: "ClassIcon2")
                        ClassroomCard(imageName: "ClassIcon3")
                        ClassroomCard(imageName: "ClassIcon4")
                        ClassroomCard(imageName: "ClassIcon5")
                    }
                }
                .padding()
                
                HStack {
                    Text("Free Courses")
                        .fontWeight(.bold)
                        .padding(.leading)
                        .font(.title2)
                    
                    Spacer()
                    
                    Text("+")
                        .font(.title2)
                        .fontWeight(.bold)
                        .frame(width: 60, height: 35, alignment: .center)
                        .foregroundColor(.white)
                        .background(Color("PrimaryColor"))
                        .cornerRadius(32)
                        .shadow(radius: 4)
                        .padding(.trailing)
                }
                
                ScrollView(.horizontal, showsIndicators: false){
                    HStack(spacing: 16){
                        ClassroomCard(imageName: "ClassIcon5")
                        ClassroomCard(imageName: "ClassIcon4")
                        ClassroomCard(imageName: "ClassIcon3")
                        ClassroomCard(imageName: "ClassIcon2")
                        ClassroomCard(imageName: "ClassIcon1")
                    }
                }
                .padding()
            }
        }
        .navigationBarHidden(true)
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
        ZStack {
            Image(imageName)
                .resizable()
                .frame(width: .infinity, height: 200, alignment: .trailing)
                .blendMode(.screen)
            VStack(alignment: .leading, spacing: 16){
                Text("Classroom 1")
                    .font(.title2)
                    .fontWeight(.bold)
                Text("This is a sample classroom one for all students of this class")
                Text("Isntructor Name")
                    .font(.caption)
            }
        }
        .padding(.all, 16)
        .frame(minWidth: 280, idealWidth: 300, maxWidth: 320, minHeight: 180, idealHeight: 200, maxHeight: 220, alignment: .leading)
        .background(Color("SecondaryColor"))
        .cornerRadius(10)
        .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 0)
    }
}

struct ClassroomCard: View {
    public var imageName : String
    var body: some View {
        ZStack {
            Image(imageName)
                .resizable()
                .frame(width: .infinity, height: 230, alignment: .trailing)
                .blendMode(.screen)
            VStack(alignment: .leading, spacing: 8){
                Text("Classroom 1")
                    .font(.title2)
                    .fontWeight(.bold)
                Text("This is a sample classroom one for all students of this class")
                Text("Isntructor Name")
                    .font(.caption)
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
        .shadow(color: Color("ShadowColor"), radius: 3, x: 0, y: 0)
    }
    
}
