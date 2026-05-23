package com.shah.cashwise.di

import com.shah.cashwise.app.AppViewModel
import com.shah.cashwise.data.repo.AppPreferencesRepositoryImpl
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.ui.screens.onboarding.OnboardingViewModel
import com.shah.cashwise.ui.screens.setpin.SetPinViewModel
import com.shah.cashwise.ui.screens.setup.SetupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val appModule = module {
    viewModelOf(::AppViewModel)
}

private val dataModule = module {
    single<AppPreferencesRepository> { AppPreferencesRepositoryImpl(get()) }
}

private val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}

private val setupModule = module {
    viewModelOf(::SetupViewModel)
}

private val setPinModule = module {
    viewModelOf(::SetPinViewModel)
}

val appModules = listOf(
    platformModule,
    dataModule,
    appModule,
    onboardingModule,
    setupModule,
    setPinModule,
)
