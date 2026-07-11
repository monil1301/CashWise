package com.shah.cashwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.app.App
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.handleDeeplinks
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {

    // Null when sync isn't configured (no SupabaseClient registered) — the
    // OAuth deep link is then irrelevant, so handling is simply skipped.
    private val supabase: SupabaseClient? by lazy {
        GlobalContext.getOrNull()?.getOrNull<SupabaseClient>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        supabase?.handleDeeplinks(intent)

        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        supabase?.handleDeeplinks(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
