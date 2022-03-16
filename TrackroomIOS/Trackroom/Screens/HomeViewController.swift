import SwiftUI

struct HomeViewController: View {
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            
            TabView{
                TabHome()
                    .tabItem() {
                        Image(systemName: "house.fill")
                        Text("Home")
                }
                TabAssignment()
                    .tabItem() {
                        Image(systemName: "list.bullet.rectangle.fill")
                        Text("Posts")
                }
                TabProfile()
                    .tabItem() {
                        Image(systemName: "gearshape.fill")
                        Text("Settings")
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
        .ignoresSafeArea()
        .navigationTitle("Trackroom")
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
    }
}

struct HomeViewController_Previews: PreviewProvider {
    static var previews: some View {
        HomeViewController()
    }
}

struct HiddenNavigationBar: ViewModifier {
    func body(content: Content) -> some View {
        content
        .navigationBarTitle("", displayMode: .inline)
        .navigationBarHidden(true)
    }
}


