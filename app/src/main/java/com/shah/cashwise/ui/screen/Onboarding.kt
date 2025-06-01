package com.shah.cashwise.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.data.model.onboarding.OnboardingPage
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.components.onboarding.OnboardingContent
import com.shah.cashwise.ui.components.onboarding.PageIndicatorRow
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.uitils.enums.ViewPosition
import com.shah.cashwise.utils.Constants
import com.shah.cashwise.utils.ImageResource

/**
 * Created by Monil on 01/06/25.
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Onboarding(
    onGetStarted: () -> Unit
) {
    var selectedOnboardingPageIndex by remember { mutableIntStateOf(0) }
    val onboardingPages = listOf(
        OnboardingPage(
            R.drawable.take_control,
            R.string.onboarding_title_1,
            R.string.onboarding_description_1
        ),
        OnboardingPage(
            R.drawable.know_money_spend,
            R.string.onboarding_title_2,
            R.string.onboarding_description_2
        ),
        OnboardingPage(
            R.drawable.plan_ahead,
            R.string.onboarding_title_3,
            R.string.onboarding_description_3
        )
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = onboardingPages[selectedOnboardingPageIndex],
                transitionSpec = {
                    slideInHorizontally { fullWidth -> fullWidth } + fadeIn() with
                            slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                },
                modifier = Modifier.weight(1f), label = ""
            ) { page ->
                OnboardingContent(page)
            }

            Spacer(modifier = Modifier.height(30.dp))

            PageIndicatorRow(onboardingPages.size, selectedOnboardingPageIndex)

            Spacer(modifier = Modifier.height(30.dp))

            PrimaryButton(
                modifier = Modifier
                    .width(180.dp)
                    .height(50.dp),
                onClick = {
                    if (selectedOnboardingPageIndex < onboardingPages.lastIndex)
                        selectedOnboardingPageIndex++
                    else
                        onGetStarted()
                },
                text = if (onboardingPages.lastIndex == selectedOnboardingPageIndex) stringResource(
                    R.string.get_started
                ) else stringResource(
                    R.string.next
                ),
                iconPosition = ViewPosition.END,
                iconSize = 32.dp,
                imageResource =
                if (onboardingPages.lastIndex == selectedOnboardingPageIndex)
                    ImageResource.None
                else
                    ImageResource.IconResource(Icons.Default.ArrowForward, "Next")
            )
        }
    }

}

@Preview
@Composable
fun PreviewOnboarding() {
    CashWiseTheme {
        Onboarding {}
    }
}
