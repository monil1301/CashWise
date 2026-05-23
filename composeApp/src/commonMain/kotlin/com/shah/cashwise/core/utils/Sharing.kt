package com.shah.cashwise.core.utils

import androidx.compose.runtime.Composable

/**
 * Returns a platform-specific share handler that opens the system share sheet
 * for the supplied text. Android uses `ACTION_SEND`; iOS uses
 * `UIActivityViewController`; Desktop falls back to copying to the system
 * clipboard until a richer integration is added.
 *
 * Returns a `(text, title) -> Unit` lambda so the @Composable can capture
 * each platform's context (Android needs `LocalContext`, iOS needs the root
 * view controller). Callers should remember the handler at composition time.
 */
@Composable
expect fun rememberShareText(): (text: String, title: String) -> Unit
