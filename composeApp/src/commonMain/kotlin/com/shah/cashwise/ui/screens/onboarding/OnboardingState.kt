package com.shah.cashwise.ui.screens.onboarding

data class OnboardingState(
    val currentPage: Int = 0,
    val totalPages: Int = 0,
) {
    val isLastPage: Boolean
        get() = totalPages > 0 && currentPage == totalPages - 1
}
