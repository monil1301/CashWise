package com.shah.cashwise.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.settings_placeholder
import com.shah.cashwise.ui.components.ScreenPlaceholder
import org.jetbrains.compose.resources.stringResource

/** TODO(settings): placeholder tab. The shell routes to it; the feature is not built yet. */
@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        title = stringResource(Res.string.settings_placeholder),
        modifier = modifier,
    )
}
