package com.shah.cashwise.ui.screens.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun OnboardingMediumLayout(
    pages: List<OnboardingPage>,
    currentPage: Int,
    isLastPage: Boolean,
    skipLabel: String,
    nextLabel: String,
    finishLabel: String,
    onSkip: () -> Unit,
    onNext: () -> Unit,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
    ) {
        OnboardingCompactLayout(
            pages = pages,
            currentPage = currentPage,
            isLastPage = isLastPage,
            skipLabel = skipLabel,
            nextLabel = nextLabel,
            finishLabel = finishLabel,
            onSkip = onSkip,
            onNext = onNext,
            onPageChanged = onPageChanged,
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 560.dp),
        )
    }
}
