package com.shah.cashwise.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

private enum class OnboardingLayout {
    PhonePortrait,
    TabletPortrait,
    TabletLandscape
}

@Composable
@Preview
fun App() {
    CashWiseTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            OnboardingScreen()
        }
    }
}

@Composable
private fun OnboardingScreen() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val layout = remember(maxWidth, maxHeight) {
            val isTablet = maxWidth >= 700.dp || maxHeight >= 700.dp
            when {
                isTablet && maxWidth > maxHeight -> OnboardingLayout.TabletLandscape
                isTablet -> OnboardingLayout.TabletPortrait
                else -> OnboardingLayout.PhonePortrait
            }
        }

        val cardModifier = when (layout) {
            OnboardingLayout.PhonePortrait -> Modifier.width(290.dp)
            OnboardingLayout.TabletPortrait -> Modifier.width(460.dp)
            OnboardingLayout.TabletLandscape -> Modifier
                .fillMaxWidth()
                .height(430.dp)
        }

        Surface(
            modifier = cardModifier,
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(6.dp)
        ) {
            when (layout) {
                OnboardingLayout.PhonePortrait -> PhonePortraitContent()
                OnboardingLayout.TabletPortrait -> TabletPortraitContent()
                OnboardingLayout.TabletLandscape -> TabletLandscapeContent()
            }
        }
    }
}

@Composable
private fun PhonePortraitContent() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaceholderImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(Modifier.height(16.dp))
        PlaceholderTextBlock(
            text = "Lorem Epsum Lorem Epsum",
            width = 150.dp,
            height = 42.dp
        )
        Spacer(Modifier.height(18.dp))
        PageIndicators()
        Spacer(Modifier.height(20.dp))
        NextButton()
    }
}

@Composable
private fun TabletPortraitContent() {
    Column(
        modifier = Modifier.padding(horizontal = 40.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaceholderImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
        )
        Spacer(Modifier.height(24.dp))
        PlaceholderTextBlock(
            text = "Lorem Epsum Lorem Epsum",
            width = 300.dp,
            height = 72.dp
        )
        Spacer(Modifier.height(28.dp))
        PageIndicators()
        Spacer(Modifier.height(28.dp))
        NextButton()
    }
}

@Composable
private fun TabletLandscapeContent() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlaceholderImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Spacer(Modifier.height(20.dp))
            PageIndicators()
            Spacer(Modifier.height(8.dp))
        }

        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlaceholderTextBlock(
                text = "Lorem Epsum Lorem Epsum",
                width = 210.dp,
                height = 165.dp
            )
            Spacer(Modifier.height(28.dp))
            NextButton()
        }
    }
}

@Composable
private fun PlaceholderImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Composable
private fun PlaceholderTextBlock(
    text: String,
    width: Dp,
    height: Dp
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(2.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PageIndicators() {
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        repeat(3) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == 0) MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.outlineVariant
                    )
            )
        }
    }
}

@Composable
private fun NextButton() {
    Button(
        modifier = Modifier.width(150.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = {}
    ) {
        Text("Next Button")
    }
}
