package com.shah.cashwise.ui.screens.onboarding

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.components.AppPager
import com.shah.cashwise.ui.components.PagerIndicators
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.components.SkipButton

private val HorizontalGutter = 24.dp
private val PageSpacing = 24.dp

@Composable
internal fun OnboardingCompactLayout(
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
    Column(
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        SkipButton(
            text = skipLabel,
            onClick = onSkip,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
            visible = !isLastPage,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // The pager spans the full screen width so adjacent pages peek in from
        // the edges; the gutter is applied as content padding instead, and
        // page spacing puts a real gap between cards while swiping.
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cardSize = maxWidth - HorizontalGutter * 2
            AppPager(
                pages = pages,
                currentPage = currentPage,
                onPageChange = onPageChanged,
                contentPadding = PaddingValues(horizontal = HorizontalGutter),
                pageSpacing = PageSpacing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardSize),
            ) { page ->
                OnboardingIllustrationCard(
                    page = page,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OnboardingMessage(
            page = pages[currentPage],
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        )

        Spacer(modifier = Modifier.height(28.dp))

        PagerIndicators(
            totalPages = pages.size,
            selectedPage = currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = if (isLastPage) finishLabel else nextLabel,
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
