package com.shah.cashwise.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import cashwise.composeapp.generated.resources.onboarding_description_1
import cashwise.composeapp.generated.resources.onboarding_description_2
import cashwise.composeapp.generated.resources.onboarding_description_3
import cashwise.composeapp.generated.resources.onboarding_get_started
import cashwise.composeapp.generated.resources.next
import cashwise.composeapp.generated.resources.skip
import cashwise.composeapp.generated.resources.onboarding_title_1
import cashwise.composeapp.generated.resources.onboarding_title_2
import cashwise.composeapp.generated.resources.onboarding_title_3
import cashwise.composeapp.generated.resources.onboarding_page_1
import cashwise.composeapp.generated.resources.onboarding_page_2
import cashwise.composeapp.generated.resources.onboarding_page_3
import com.shah.cashwise.di.appModules
import com.shah.cashwise.ui.screens.onboarding.OnboardingPage
import com.shah.cashwise.ui.screens.onboarding.OnboardingScreen
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            var showOnboarding by rememberSaveable { mutableStateOf(true) }
            val pages = remember {
                listOf(
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
            }

            if (showOnboarding) {
                OnboardingScreen(
                    pages = pages,
                    onFinished = { showOnboarding = false },
                    skipLabel = stringResource(Res.string.skip),
                    nextLabel = stringResource(Res.string.next),
                    finishLabel = stringResource(Res.string.onboarding_get_started),
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize(),
                )
            } else {
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
}
