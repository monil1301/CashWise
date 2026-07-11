package com.shah.cashwise.ui.screens.budgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.budgets_placeholder
import com.shah.cashwise.ui.components.ScreenPlaceholder
import org.jetbrains.compose.resources.stringResource

/** TODO(budgets): placeholder tab. The shell routes to it; the feature is not built yet. */
@Composable
fun BudgetsScreen(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        title = stringResource(Res.string.budgets_placeholder),
        modifier = modifier,
    )
}
