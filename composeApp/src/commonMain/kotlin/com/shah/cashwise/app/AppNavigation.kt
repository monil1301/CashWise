package com.shah.cashwise.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import cashwise.composeapp.generated.resources.next
import cashwise.composeapp.generated.resources.onboarding_description_1
import cashwise.composeapp.generated.resources.onboarding_description_2
import cashwise.composeapp.generated.resources.onboarding_description_3
import cashwise.composeapp.generated.resources.onboarding_get_started
import cashwise.composeapp.generated.resources.onboarding_page_1
import cashwise.composeapp.generated.resources.onboarding_page_2
import cashwise.composeapp.generated.resources.onboarding_page_3
import cashwise.composeapp.generated.resources.onboarding_title_1
import cashwise.composeapp.generated.resources.onboarding_title_2
import cashwise.composeapp.generated.resources.onboarding_title_3
import cashwise.composeapp.generated.resources.skip
import com.shah.cashwise.ui.screens.onboarding.OnboardingPage
import com.shah.cashwise.ui.screens.onboarding.OnboardingScreen
import com.shah.cashwise.ui.screens.setup.SetupScreen
import com.shah.cashwise.ui.screens.signin.SignInScreen
import com.shah.cashwise.ui.screens.welcome.WelcomeScreen
import org.jetbrains.compose.resources.stringResource

/**
 * Top-level destination switch. Onboarding shows on first launch only — once
 * [AppState.onboardingCompleted] is persisted, later launches go straight to
 * the welcome screen (the offline-first, not-signed-in entry point), then into
 * the setup flow.
 */
@Composable
internal fun AppNavigation(
    state: AppState,
    onOnboardingFinished: () -> Unit,
) {
    val pages = remember { onboardingPages() }
    var showWelcome by rememberSaveable { mutableStateOf(true) }
    var showSignIn by rememberSaveable { mutableStateOf(false) }
    var setupCompleted by rememberSaveable { mutableStateOf(false) }

    // React to auth-session changes. Signing in (from the sign-in screen, or a
    // session restored on launch) dismisses the welcome/sign-in gate and drops
    // into setup; signing out returns to the offline-first welcome screen. This
    // only fires when isSignedIn actually flips, so offline users who tapped
    // "Continue Offline" are never pulled back to welcome.
    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            showSignIn = false
            showWelcome = false
        } else {
            showWelcome = true
        }
    }

    when {
        // Preferences still loading — render nothing rather than flash onboarding.
        state.isLoading -> Unit

        // Onboarding done but session not yet restored — wait rather than flash
        // welcome before we know whether a real session exists.
        state.onboardingCompleted && state.isSessionResolving -> Unit

        !state.onboardingCompleted -> {
            OnboardingScreen(
                pages = pages,
                onFinished = onOnboardingFinished,
                skipLabel = stringResource(Res.string.skip),
                nextLabel = stringResource(Res.string.next),
                finishLabel = stringResource(Res.string.onboarding_get_started),
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
            )
        }

        showSignIn -> {
            SignInScreen(
                onBack = { showSignIn = false },
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
            )
        }

        showWelcome -> {
            WelcomeScreen(
                onContinueOffline = { showWelcome = false },
                onSignIn = { showSignIn = true },
                modifier = Modifier.fillMaxSize(),
            )
        }

        !setupCompleted -> {
            SetupScreen(
                onExit = { showWelcome = true },
                onFinished = { setupCompleted = true },
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
            )
        }

        else -> {
            Box(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}

/** The onboarding intro pages, in display order. */
private fun onboardingPages(): List<OnboardingPage> = listOf(
    OnboardingPage(
        image = Res.drawable.onboarding_page_1,
        title = Res.string.onboarding_title_1,
        description = Res.string.onboarding_description_1,
    ),
    OnboardingPage(
        image = Res.drawable.onboarding_page_2,
        title = Res.string.onboarding_title_2,
        description = Res.string.onboarding_description_2,
    ),
    OnboardingPage(
        image = Res.drawable.onboarding_page_3,
        title = Res.string.onboarding_title_3,
        description = Res.string.onboarding_description_3,
    ),
)
