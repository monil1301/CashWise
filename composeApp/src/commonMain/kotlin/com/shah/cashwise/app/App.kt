package com.shah.cashwise.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    CashWiseTheme {
        if (LocalInspectionMode.current) {
            // Preview tooling can't start Koin or supply a DataStore — render the
            // first real destination without touching persisted state.
            AppNavigation(
                state = AppState(
                    isLoading = false,
                    onboardingCompleted = false,
                    sessionStatus = AuthSessionStatus.SignedOut,
                ),
                onOnboardingFinished = {},
            )
        } else {
            // Koin is started globally by the platform entry point (initKoin),
            // so koinViewModel resolves against that container directly.
            val viewModel = koinViewModel<AppViewModel>()
            val state by viewModel.state.collectAsState()
            AppNavigation(
                state = state,
                onOnboardingFinished = viewModel::onOnboardingFinished,
            )
        }
    }
}
