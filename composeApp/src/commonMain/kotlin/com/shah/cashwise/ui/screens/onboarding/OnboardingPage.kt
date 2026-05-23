package com.shah.cashwise.ui.screens.onboarding

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/** One onboarding intro page — its illustration, title, and description. */
data class OnboardingPage(
    val image: DrawableResource,
    val title: StringResource,
    val description: StringResource,
)
