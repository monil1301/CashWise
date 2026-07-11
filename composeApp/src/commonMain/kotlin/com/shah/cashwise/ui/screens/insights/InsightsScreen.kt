package com.shah.cashwise.ui.screens.insights

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.insights_placeholder
import com.shah.cashwise.ui.components.ScreenPlaceholder
import org.jetbrains.compose.resources.stringResource

/** TODO(insights): placeholder tab. The shell routes to it; the feature is not built yet. */
@Composable
fun InsightsScreen(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        title = stringResource(Res.string.insights_placeholder),
        modifier = modifier,
    )
}
