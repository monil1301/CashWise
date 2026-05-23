package com.shah.cashwise.ui.screens.setpin.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.screens.setpin.SetPinAction
import com.shah.cashwise.ui.screens.setpin.SetPinState

/**
 * Medium-width Set PIN layout: the stacked compact layout, width-capped and
 * centered so the form doesn't stretch on tablets.
 */
@Composable
internal fun SetPinMediumLayout(
    state: SetPinState,
    onAction: (SetPinAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        SetPinCompactLayout(
            state = state,
            onAction = onAction,
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 560.dp),
        )
    }
}
