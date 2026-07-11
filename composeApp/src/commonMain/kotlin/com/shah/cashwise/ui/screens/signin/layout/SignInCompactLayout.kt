package com.shah.cashwise.ui.screens.signin.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shah.cashwise.ui.screens.signin.SignInAction
import com.shah.cashwise.ui.screens.signin.SignInState
import com.shah.cashwise.ui.screens.signin.SignInStep

/**
 * Phone layout of the Sign in to Sync screen. Dispatches to the active step —
 * the provider list, email entry, or code entry. Also reused, width-capped, by
 * the medium/expanded breakpoints.
 */
@Composable
internal fun SignInCompactLayout(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.step) {
        SignInStep.Providers -> SignInProvidersStep(state, onAction, modifier)
        SignInStep.EmailEntry -> SignInEmailStep(state, onAction, modifier)
        SignInStep.CodeEntry -> SignInCodeStep(state, onAction, modifier)
    }
}
