package com.shah.cashwise.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.onboarding_description_1
import cashwise.composeapp.generated.resources.onboarding_description_2
import cashwise.composeapp.generated.resources.onboarding_description_3
import cashwise.composeapp.generated.resources.onboarding_page_1
import cashwise.composeapp.generated.resources.onboarding_page_2
import cashwise.composeapp.generated.resources.onboarding_page_3
import cashwise.composeapp.generated.resources.onboarding_title_1
import cashwise.composeapp.generated.resources.onboarding_title_2
import cashwise.composeapp.generated.resources.onboarding_title_3
import com.shah.cashwise.navigation.Destination
import com.shah.cashwise.navigation.appNavGraph
import com.shah.cashwise.ui.screens.onboarding.OnboardingPage

/**
 * NavHost call site. Resolves where the app opens from *persisted* state, then hands off to
 * [appNavGraph]; screens navigate between themselves via typed [Destination]s.
 *
 * Two transitions are data-driven rather than callback-driven, because they reflect stored
 * facts rather than a button press:
 *  - a wallet existing means setup is done → [Destination.Home];
 *  - a session existing means the sign-in gate is satisfied → [Destination.Setup].
 *
 * Deriving them from state keeps the UI from ever disagreeing with what is actually stored.
 */
@Composable
internal fun AppNavigation(
    state: AppState,
    onOnboardingFinished: () -> Unit,
) {
    // Nothing is known yet — render nothing rather than flash the wrong screen.
    if (state.isLoading) return

    val startDestination = startDestinationFor(state) ?: return
    val navController = rememberNavController()
    val pages = remember { onboardingPages() }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        appNavGraph(
            navController = navController,
            onboardingPages = pages,
            onOnboardingFinished = onOnboardingFinished,
        )
    }

    // Setup completing (i.e. the wallet landing in the database) is what admits the user to
    // the app. Everything before Home is popped: those gates are not somewhere to go Back to.
    LaunchedEffect(state.setupCompleted) {
        if (state.setupCompleted &&
            navController.currentDestination?.hasRoute<Destination.Home>() != true
        ) {
            navController.navigate(Destination.Home) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Signing in satisfies the welcome/sign-in gate; drop the user into setup.
    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn && !state.setupCompleted) {
            navController.navigate(Destination.Setup) {
                popUpTo(Destination.Welcome)
            }
        }
    }
}

/**
 * Where the app opens. `null` means "not yet decidable" — keep rendering nothing.
 *
 * The session is only consulted for the branch that actually needs it. Restoring a Supabase
 * session hits the network, so a user who already has a wallet must not be made to wait on
 * it: [AppState.setupCompleted] is checked first.
 */
private fun startDestinationFor(state: AppState): Destination? = when {
    !state.onboardingCompleted -> Destination.Onboarding
    state.setupCompleted -> Destination.Home
    state.isSessionResolving -> null
    state.isSignedIn -> Destination.Setup
    else -> Destination.Welcome
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
