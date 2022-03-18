import SwiftUI

struct HomeView: View {
    //@Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            //                NavigationLink(destination: WelcomeViewController()) {
            //                    Text("Logout")
            //                        .fontWeight(.bold)
            //                        .foregroundColor(Color("PrimaryColor"))
            //                        .padding(.top, 100)
            //                }
            //                .onTapGesture {
            //                    self.presentationMode.wrappedValue.dismiss()
            //                }
            
            TabView{
                TabClassrooms()
                    .tabItem() {
                        Image(systemName: "house.fill")
                        Text("Home")
                    }
                TabNotifications()
                    .tabItem() {
                        Image(systemName: "list.bullet.rectangle.fill")
                        Text("Notifications")
                    }
                TabProfile()
                    .tabItem() {
                        Image(systemName: "person.fill")
                        Text("Profile")
                    }
            }
            .onAppear {
                if #available(iOS 15.0, *) {
                    let appearance = UITabBarAppearance()
                    UITabBar.appearance().scrollEdgeAppearance = appearance
                }
            }
            .accentColor(Color("PrimaryColor"))
        }
//        .padding(.bottom, 150)
//        .navigationTitle("Trackroom")
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
//        .ignoresSafeArea()

    }
}

struct HomeViewController_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
        .navigationBarTitle("", displayMode: .inline)
        .navigationBarHidden(true)
    }
}


