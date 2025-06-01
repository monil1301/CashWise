package com.shah.cashwise.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.data.model.onboarding.OnboardingPage
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun OnboardingContent(onboardingPage: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(onboardingPage.imageRes),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            text = stringResource(onboardingPage.titleRes),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            text = stringResource(onboardingPage.descriptionRes),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }

}

@Preview
@Composable
fun PreviewOnboardingInfo() {
    CashWiseTheme {
        OnboardingContent(
            OnboardingPage(
                R.drawable.take_control,
                R.string.onboarding_title_1,
                R.string.onboarding_description_1
            )
        )
    }
}
