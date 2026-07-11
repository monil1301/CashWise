package com.shah.cashwise.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.home_placeholder
import com.shah.cashwise.ui.components.ScreenPlaceholder
import org.jetbrains.compose.resources.stringResource

/** TODO(home): placeholder tab. The shell routes to it; the feature is not built yet. */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        title = stringResource(Res.string.home_placeholder),
        modifier = modifier,
    )
}
