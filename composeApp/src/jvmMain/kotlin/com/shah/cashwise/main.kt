package com.shah.cashwise

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.shah.cashwise.app.App
import com.shah.cashwise.di.initKoin
import org.koin.core.context.GlobalContext
import java.awt.Dimension

fun main() {
    if (GlobalContext.getOrNull() == null) {
        initKoin()
    }
    application {
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
}
