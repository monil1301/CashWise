package com.shah.cashwise.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.di.appModules
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            if (LocalInspectionMode.current) {
                // Preview tooling can't supply a DataStore — render the first
                // real destination without touching persisted preferences.
                AppNavigation(
                    state = AppState(isLoading = false, onboardingCompleted = false),
                    onOnboardingFinished = {},
                )
            } else {
                val viewModel = koinViewModel<AppViewModel>()
                val state by viewModel.state.collectAsState()
                AppNavigation(
                    state = state,
                    onOnboardingFinished = viewModel::onOnboardingFinished,
                )
            }
        }
    }
}
