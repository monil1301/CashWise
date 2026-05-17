package com.shah.cashwise.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import com.shah.cashwise.ui.components.AppPager
import com.shah.cashwise.ui.components.PagerIndicators
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.components.SkipButton
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun OnboardingExpandedLayout(
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
        modifier = modifier.padding(horizontal = 40.dp, vertical = 28.dp),
    ) {
        // Top bar: brand wordmark on the left, Skip on the right.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            SkipButton(
                text = skipLabel,
                onClick = onSkip,
                visible = !isLastPage,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left: the largest square illustration card that fits, centered.
            BoxWithConstraints(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                val side = minOf(maxWidth, maxHeight)
                AppPager(
                    pages = pages,
                    currentPage = currentPage,
                    onPageChange = onPageChanged,
                    modifier = Modifier.size(side),
                ) { page ->
                    OnboardingIllustrationCard(
                        page = page,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            // Right: copy, indicators and primary action, vertically centered
            // and left-aligned.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(max = 460.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                OnboardingMessage(
                    page = pages[currentPage],
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    textAlign = TextAlign.Start,
                )

                Spacer(modifier = Modifier.height(28.dp))

                PagerIndicators(
                    totalPages = pages.size,
                    selectedPage = currentPage,
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = if (isLastPage) finishLabel else nextLabel,
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
