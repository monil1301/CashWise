package com.shah.cashwise.ui.screens.onboarding

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import cashwise.composeapp.generated.resources.onboarding_page_1
import cashwise.composeapp.generated.resources.onboarding_page_2
import cashwise.composeapp.generated.resources.onboarding_page_3
import com.shah.cashwise.di.appModules
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

private enum class OnboardingLayoutType {
    Compact,
    Medium,
    Expanded,
}

@Composable
fun OnboardingScreen(
    pages: List<OnboardingPage>,
    onFinished: () -> Unit,
    skipLabel: String,
    nextLabel: String,
    finishLabel: String,
    modifier: Modifier = Modifier,
) {
    if (pages.isEmpty()) return

    val viewModel = koinViewModel<OnboardingViewModel>()
    val state by viewModel.state.collectAsState()
    val onFinishedState by rememberUpdatedState(onFinished)

    LaunchedEffect(pages.size) {
        viewModel.updateTotalPages(pages.size)
    }

    val safeCurrentPage = state.currentPage.coerceIn(0, pages.lastIndex)

    val handleAction: (OnboardingAction) -> Unit = { action ->
        val shouldFinish = viewModel.onAction(action)
        if (shouldFinish) onFinishedState()
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val layoutType = when {
            maxWidth < 600.dp -> OnboardingLayoutType.Compact
            maxWidth < 840.dp -> OnboardingLayoutType.Medium
            else -> OnboardingLayoutType.Expanded
        }

        when (layoutType) {
            OnboardingLayoutType.Compact -> {
                OnboardingCompactLayout(
                    pages = pages,
                    currentPage = safeCurrentPage,
                    isLastPage = state.isLastPage,
                    skipLabel = skipLabel,
                    nextLabel = nextLabel,
                    finishLabel = finishLabel,
                    onSkip = { handleAction(OnboardingAction.Skip) },
                    onNext = { handleAction(OnboardingAction.Next) },
                    onPageChanged = { handleAction(OnboardingAction.PageChanged(it)) },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            OnboardingLayoutType.Medium -> {
                OnboardingMediumLayout(
                    pages = pages,
                    currentPage = safeCurrentPage,
                    isLastPage = state.isLastPage,
                    skipLabel = skipLabel,
                    nextLabel = nextLabel,
                    finishLabel = finishLabel,
                    onSkip = { handleAction(OnboardingAction.Skip) },
                    onNext = { handleAction(OnboardingAction.Next) },
                    onPageChanged = { handleAction(OnboardingAction.PageChanged(it)) },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            OnboardingLayoutType.Expanded -> {
                OnboardingExpandedLayout(
                    pages = pages,
                    currentPage = safeCurrentPage,
                    isLastPage = state.isLastPage,
                    skipLabel = skipLabel,
                    nextLabel = nextLabel,
                    finishLabel = finishLabel,
                    onSkip = { handleAction(OnboardingAction.Skip) },
                    onNext = { handleAction(OnboardingAction.Next) },
                    onPageChanged = { handleAction(OnboardingAction.PageChanged(it)) },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Preview(name = "Onboarding Compact", widthDp = 390, heightDp = 844)
@Composable
private fun OnboardingScreenCompactPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            val appName = stringResource(Res.string.app_name)
            OnboardingScreen(
                pages = previewPages(),
                onFinished = {},
                skipLabel = appName,
                nextLabel = appName,
                finishLabel = appName,
            )
        }
    }
}

@Preview(name = "Onboarding Medium", widthDp = 800, heightDp = 1000)
@Composable
private fun OnboardingScreenMediumPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            val appName = stringResource(Res.string.app_name)
            OnboardingScreen(
                pages = previewPages(),
                onFinished = {},
                skipLabel = appName,
                nextLabel = appName,
                finishLabel = appName,
            )
        }
    }
}

@Preview(name = "Onboarding Expanded", widthDp = 1200, heightDp = 900)
@Composable
private fun OnboardingScreenExpandedPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            val appName = stringResource(Res.string.app_name)
            OnboardingScreen(
                pages = previewPages(),
                onFinished = {},
                skipLabel = appName,
                nextLabel = appName,
                finishLabel = appName,
            )
        }
    }
}

private fun previewPages(): List<OnboardingPage> {
    return listOf(
        OnboardingPage(
            image = Res.drawable.onboarding_page_1,
            title = Res.string.app_name,
            description = Res.string.app_name,
        ),
        OnboardingPage(
            image = Res.drawable.onboarding_page_2,
            title = Res.string.app_name,
            description = Res.string.app_name,
        ),
        OnboardingPage(
            image = Res.drawable.onboarding_page_3,
            title = Res.string.app_name,
            description = Res.string.app_name,
        ),
    )
}
