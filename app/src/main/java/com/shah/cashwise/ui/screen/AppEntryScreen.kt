package com.shah.cashwise.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.ui.uitils.enums.EntryScreens
import com.shah.cashwise.ui.viewmodel.AppEntryViewModel

/**
 * Created by Monil on 06/06/25.
 */

@Composable
fun AppEntryScreen(viewModel: AppEntryViewModel = hiltViewModel()) {
    viewModel.isUserLoggedIn()
    val isLoggedIn by viewModel.loggedInState.collectAsState()

    if (isLoggedIn == null)
        return

    var currentScreen by remember { mutableStateOf(if (isLoggedIn == true) EntryScreens.UNLOCK_PIN else EntryScreens.ONBOARDING) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentScreen == EntryScreens.MAIN_NAV)
            Home()

        AnimatedVisibility(
            visible = currentScreen == EntryScreens.PIN_SETUP,
            enter = slideInVertically(
                initialOffsetY = { -it }
            ),
            exit = fadeOut()
        ) {
            PinSetupScreen {
                currentScreen = EntryScreens.MAIN_NAV
            }
        }

        AnimatedVisibility(
            visible = currentScreen == EntryScreens.AUTH,
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            AuthScreen { currentScreen = EntryScreens.PIN_SETUP }
        }

        AnimatedVisibility(
            visible = currentScreen == EntryScreens.ONBOARDING,
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            Onboarding { currentScreen = EntryScreens.AUTH }
        }

        AnimatedVisibility(
            visible = currentScreen == EntryScreens.UNLOCK_PIN,
            exit = fadeOut()
        ) {
            PinUnlockScreen {

                currentScreen = EntryScreens.MAIN_NAV
            }
        }
    }

}
