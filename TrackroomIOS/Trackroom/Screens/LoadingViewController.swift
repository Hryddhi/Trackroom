//
//  LoadingViewController.swift
//  Trackroom
//
//  Created by Rifatul Islam on 28/2/22.
//

import SwiftUI

struct LoadingViewController: View {
    @State private var hasTimeElapsed = false
    
    var body: some View {
        ZStack{
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            ProgressView("Logging You In!")
                .task {
                    await delayText()
                }
        }
    
    }
    
    private func delayText() async {
            // Delay of 7.5 seconds (1 second = 1_000_000_000 nanoseconds)
            try? await Task.sleep(nanoseconds: 1_000_000_000)
            hasTimeElapsed = true
        }
}

struct LoadingViewController_Previews: PreviewProvider {
    static var previews: some View {
        LoadingViewController()
    }
}
