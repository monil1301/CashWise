package com.shah.cashwise

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shah.cashwise.app.App
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CashWise",
    ) {
        LaunchedEffect(Unit) {
            window.minimumSize = Dimension(1000, 700)
        }
        App()
    }
}
