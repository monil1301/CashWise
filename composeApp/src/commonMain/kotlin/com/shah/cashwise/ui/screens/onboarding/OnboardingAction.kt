package com.shah.cashwise.ui.screens.onboarding

sealed interface OnboardingAction {
    data object Next : OnboardingAction
    data object Skip : OnboardingAction
    data class PageChanged(val pageIndex: Int) : OnboardingAction
}
