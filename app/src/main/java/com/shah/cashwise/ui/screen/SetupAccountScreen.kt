package com.shah.cashwise.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.ui.uitils.enums.SetupAccountScreen

/**
 * Created by Monil on 07/06/25.
 */

@Composable
fun SetupAccountScreen(onComplete: () -> Unit) {
    var currentScreen by remember { mutableStateOf(SetupAccountScreen.START) }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = currentScreen == SetupAccountScreen.END,
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
//            TickAnimation()
        }

        AnimatedVisibility(
            visible = currentScreen == SetupAccountScreen.ADD_ACCOUNT,
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            AddNewAccountScreen(
                onBackClick = {
                    currentScreen = SetupAccountScreen.START
                },
                onContinueClick = {
                    currentScreen = SetupAccountScreen.END
                })
        }

        AnimatedVisibility(
            visible = currentScreen == SetupAccountScreen.START,
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            SetupStartScreen {
                currentScreen = SetupAccountScreen.ADD_ACCOUNT
            }
        }
    }
}

@Preview
@Composable
fun PreviewSetupAccountScreen() {

}
