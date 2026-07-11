package com.shah.cashwise.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.next
import cashwise.composeapp.generated.resources.onboarding_get_started
import cashwise.composeapp.generated.resources.skip
import com.shah.cashwise.ui.screens.home.HomeScreen
import com.shah.cashwise.ui.screens.onboarding.OnboardingPage
import com.shah.cashwise.ui.screens.onboarding.OnboardingScreen
import com.shah.cashwise.ui.screens.setup.SetupScreen
import com.shah.cashwise.ui.screens.signin.SignInScreen
import com.shah.cashwise.ui.screens.welcome.WelcomeScreen
import org.jetbrains.compose.resources.stringResource

/**
 * The app's navigation graph. Screens never hardcode route strings — they call the typed
 * [Destination] entries here.
 *
 * Note what this graph does *not* do: it never navigates to [Destination.Home] itself.
 * Reaching Home means "a wallet exists", which is persisted state, so that transition is
 * driven by observing the data (see `AppNavigation`) rather than by a screen callback.
 * A screen cannot navigate past a setup that failed to save.
 */
fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    onboardingPages: List<OnboardingPage>,
    onOnboardingFinished: () -> Unit,
) {
    composable<Destination.Onboarding> {
        OnboardingScreen(
            pages = onboardingPages,
            onFinished = {
                onOnboardingFinished()
                navController.navigate(Destination.Welcome) {
                    // Onboarding is shown once — never allow Back into it.
                    popUpTo(Destination.Onboarding) { inclusive = true }
                }
            },
            skipLabel = stringResource(Res.string.skip),
            nextLabel = stringResource(Res.string.next),
            finishLabel = stringResource(Res.string.onboarding_get_started),
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        )
    }

    composable<Destination.Welcome> {
        WelcomeScreen(
            onContinueOffline = { navController.navigate(Destination.Setup) },
            onSignIn = { navController.navigate(Destination.SignIn) },
            modifier = Modifier.fillMaxSize(),
        )
    }

    composable<Destination.SignIn> {
        SignInScreen(
            onBack = { navController.popBackStack() },
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        )
    }

    composable<Destination.Setup> {
        SetupScreen(
            onExit = { navController.popBackStack() },
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        )
    }

    composable<Destination.Home> {
        HomeScreen(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        )
    }
}
