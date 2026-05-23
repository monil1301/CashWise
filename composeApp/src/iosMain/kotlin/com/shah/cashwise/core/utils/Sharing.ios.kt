package com.shah.cashwise.core.utils

import androidx.compose.runtime.Composable
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Composable
actual fun rememberShareText(): (text: String, title: String) -> Unit = { text, _ ->
    val controller = UIActivityViewController(
        activityItems = listOf(text),
        applicationActivities = null,
    )
    UIApplication.sharedApplication
        .keyWindow
        ?.rootViewController
        ?.presentViewController(controller, animated = true, completion = null)
}
