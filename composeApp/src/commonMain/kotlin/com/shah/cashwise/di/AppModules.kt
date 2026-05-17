package com.shah.cashwise.di

import com.shah.cashwise.ui.screens.onboarding.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}

val appModules = listOf(
    onboardingModule,
)
