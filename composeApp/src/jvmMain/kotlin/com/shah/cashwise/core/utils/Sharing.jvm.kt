package com.shah.cashwise.core.utils

import androidx.compose.runtime.Composable
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Composable
actual fun rememberShareText(): (text: String, title: String) -> Unit = { text, _ ->
    // TODO(share-desktop): wire to a richer Desktop share affordance. For now
    // we copy the text to the system clipboard so the user can paste it
    // wherever they like.
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(text), null)
}
