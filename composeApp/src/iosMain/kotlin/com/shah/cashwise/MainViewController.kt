package com.shah.cashwise

import androidx.compose.ui.window.ComposeUIViewController
import com.shah.cashwise.app.App
import com.shah.cashwise.di.initKoin
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import org.koin.mp.KoinPlatform
import platform.Foundation.NSURL
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    // KoinPlatform is the multiplatform-safe accessor (GlobalContext is JVM/Android only).
    if (KoinPlatform.getKoinOrNull() == null) {
        initKoin()
    }
    return ComposeUIViewController { App() }
}

/**
 * Forwards an incoming custom-scheme URL (com.shah.cashwise://auth-callback) to
 * Supabase so the OAuth redirect establishes the session. Called from the SwiftUI
 * entry point's `.onOpenURL`. No-op when sync isn't configured (no [SupabaseClient]
 * registered) — mirrors `MainActivity.handleDeeplinks` on Android.
 */
fun handleDeeplink(url: NSURL) {
    val supabase = KoinPlatform.getKoinOrNull()?.getOrNull<SupabaseClient>() ?: return
    // Kotlin/Native aborts the process on any undeclared exception crossing the
    // ObjC boundary, so nothing may escape a function called from Swift.
    runCatching { supabase.handleDeeplinks(url) }
}
