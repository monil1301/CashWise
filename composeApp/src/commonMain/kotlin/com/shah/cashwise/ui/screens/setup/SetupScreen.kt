package com.shah.cashwise.ui.screens.setup

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.repo.AppLockRepository
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.domain.repo.WalletRepository
import com.shah.cashwise.ui.screens.setpin.SetPinScreen
import com.shah.cashwise.ui.screens.setup.layout.SetupCompactLayout
import com.shah.cashwise.ui.screens.setup.layout.SetupExpandedLayout
import com.shah.cashwise.ui.screens.setup.layout.SetupMediumLayout
import com.shah.cashwise.ui.theme.CashWiseTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private enum class SetupLayoutType {
    Compact,
    Medium,
    Expanded,
}

/**
 * Multi-step first-run setup flow. Step count follows the selected
 * [WalletType] — Solo is four steps, Shared adds an "Invite members" step at
 * the end (see [SetupState.totalSteps]).
 *
 * The Lock step can hand off to [SetPinScreen] as a sub-route (the PIN screen
 * has its own chrome, so it is not a counted step). Confirming the PIN advances
 * the underlying flow by one step; backing out returns to the Lock step.
 *
 * Finishing the last step persists the wallet and flips the stored `setupCompleted`
 * flag; `AppNavigation` observes that flag and moves on, so there is no completion
 * callback here — the screen can never navigate away from a setup that failed to save.
 *
 * @param onExit invoked when the user backs out of the first step.
 */
@Composable
fun SetupScreen(
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SetupViewModel>()
    val state by viewModel.state.collectAsState()
    val onExitState by rememberUpdatedState(onExit)

    val handleAction: (SetupAction) -> Unit = { action ->
        when (viewModel.onAction(action)) {
            SetupNavResult.ExitToWelcome -> onExitState()
            null -> Unit
        }
    }

    if (state.showSetPin) {
        SetPinScreen(
            onExit = { handleAction(SetupAction.SetPinDismissed) },
            onConfirmed = { pin -> handleAction(SetupAction.SetPinConfirmed(pin)) },
            modifier = modifier.fillMaxSize(),
        )
        return
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> SetupLayoutType.Compact
            maxWidth < 840.dp -> SetupLayoutType.Medium
            else -> SetupLayoutType.Expanded
        }

        when (layoutType) {
            SetupLayoutType.Compact -> SetupCompactLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetupLayoutType.Medium -> SetupMediumLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetupLayoutType.Expanded -> SetupExpandedLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/**
 * Previews must not pull in [appModules]: that graph builds the real SQLite driver and
 * DataStore, which need an initialised Android Application — absent in preview tooling, so
 * the preview would crash. These no-op fakes let the screen render on its own.
 */
private val previewModule = module {
    single<WalletRepository> {
        object : WalletRepository {
            override val wallets: Flow<List<Wallet>> = flowOf(emptyList())
            override val hasWallet: Flow<Boolean> = flowOf(false)
            override suspend fun createWallet(wallet: Wallet): Result<Unit> = Result.success(Unit)
        }
    }
    single<AppPreferencesRepository> {
        object : AppPreferencesRepository {
            override val onboardingCompleted: Flow<Boolean> = flowOf(true)
            override suspend fun setOnboardingCompleted(completed: Boolean) = Unit
            override val appLockEnabled: Flow<Boolean> = flowOf(false)
            override suspend fun setAppLockEnabled(enabled: Boolean) = Unit
        }
    }
    single<AppLockRepository> {
        object : AppLockRepository {
            override val isLockEnabled: Flow<Boolean> = flowOf(false)
            override suspend fun setPin(pin: String): Result<Unit> = Result.success(Unit)
            override suspend fun verifyPin(pin: String): Boolean = false
            override suspend fun clearPin() = Unit
        }
    }
    viewModel { SetupViewModel(get(), get(), get()) }
}

@Preview(name = "Setup Compact", widthDp = 390, heightDp = 844)
@Composable
private fun SetupScreenCompactPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        CashWiseTheme {
            SetupScreen(onExit = {})
        }
    }
}

@Preview(name = "Setup Medium", widthDp = 800, heightDp = 1000)
@Composable
private fun SetupScreenMediumPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        CashWiseTheme {
            SetupScreen(onExit = {})
        }
    }
}

@Preview(name = "Setup Expanded", widthDp = 1200, heightDp = 900)
@Composable
private fun SetupScreenExpandedPreview() {
    KoinApplication(application = { modules(previewModule) }) {
        CashWiseTheme {
            SetupScreen(onExit = {})
        }
    }
}
