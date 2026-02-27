package com.shah.cashwise

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shah.cashwise.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CashWise",
    ) {
        App()
    }
}