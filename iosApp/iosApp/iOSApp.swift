import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                // Route the Supabase OAuth redirect (com.shah.cashwise://auth-callback)
                // back into the shared code so the Google sign-in session lands.
                .onOpenURL { url in
                    MainViewControllerKt.handleDeeplink(url: url)
                }
        }
    }
}