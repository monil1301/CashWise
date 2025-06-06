package com.shah.cashwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.ui.screen.AppEntryScreen
import com.shah.cashwise.ui.screen.AuthScreen
import com.shah.cashwise.ui.screen.Onboarding
import com.shah.cashwise.ui.screen.PinSetupScreen
import com.shah.cashwise.ui.screen.PinUnlockScreen
import com.shah.cashwise.ui.theme.CashWiseTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Monil on 25/04/25.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashWiseTheme {
                AppEntryScreen()
            }
        }
    }
}
