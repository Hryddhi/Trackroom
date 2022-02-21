import SwiftUI

struct WelcomeViewController: View {
    var body: some View {
        NavigationView {
            ZStack{
                Color("BgColor")
                     .ignoresSafeArea()
                VStack {
                    Text("Welcome To Trackroom")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                        .padding(.top, 32)
                    
                    Image("WelcomeBanner")
                        .resizable()
                        .scaledToFit()
                        .frame(minWidth: 200, idealWidth: 300, maxWidth: 400, minHeight: 200, idealHeight: 300, maxHeight: 400, alignment: .center)
                        .padding(.bottom,16)
                        .padding(.horizontal, 8)
                    
                    Rectangle()
                        .frame(width: .infinity, height: 2, alignment: .center)
                        .foregroundColor(Color.gray)
                        .padding(.horizontal, 16)
                        .padding(.top)
                    
                    Spacer()
                    
                    btnGetStarted()
                    
                    btnLogin()
            
                }
                .padding()
            }
            .navigationBarHidden(true)
        }
        
        }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        WelcomeViewController()
    }
}

struct btnGetStarted: View {
    var body: some View {
        NavigationLink("Register Now", destination: registerView())
            .font(.title2)
            .foregroundColor(.white)
            .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 55, idealHeight: 55, maxHeight: 55, alignment: .center)
            .background(Color("PrimaryColor"))
            .cornerRadius(32)
            .shadow(radius: 4)

        
            
        Text("Get Started")
            .font(.title2)
            .fontWeight(.bold)
            .foregroundColor(.white)
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                minHeight: 0,
                maxHeight: 55,
                alignment: .center
            )
            .background(Color("PrimaryColor"))
            .cornerRadius(32)
            .shadow(radius: 4)
            .padding(.bottom, 8)
    }
}

struct btnLogin: View {
    var body: some View {
        Text("Login")
            .font(.title2)
            .fontWeight(.bold)
            .foregroundColor(Color("PrimaryColor"))
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                minHeight: 0,
                maxHeight: 55,
                alignment: .center
            )
            .background(Color("SecondaryColor"))
            .cornerRadius(32)
            .shadow(radius: 4)
    }
}
