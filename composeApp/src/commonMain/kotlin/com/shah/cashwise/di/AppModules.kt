package com.shah.cashwise.di

import com.shah.cashwise.app.AppViewModel
import com.shah.cashwise.core.config.SupabaseConfig
import com.shah.cashwise.data.repo.AppPreferencesRepositoryImpl
import com.shah.cashwise.data.repo.AuthRepositoryImpl
import com.shah.cashwise.data.repo.OfflineAuthRepository
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.domain.repo.AuthRepository
import com.shah.cashwise.ui.screens.onboarding.OnboardingViewModel
import com.shah.cashwise.ui.screens.setpin.SetPinViewModel
import com.shah.cashwise.ui.screens.setup.SetupViewModel
import com.shah.cashwise.ui.screens.signin.SignInViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appModule = module {
    viewModelOf(::AppViewModel)
}

private val dataModule = module {
    single<AppPreferencesRepository> { AppPreferencesRepositoryImpl(get()) }
}

/**
 * Whether Supabase credentials were baked in at build time. When false the app
 * runs offline-only and never constructs a [SupabaseClient].
 */
private val isSyncConfigured: Boolean =
    SupabaseConfig.URL.isNotBlank() && SupabaseConfig.ANON_KEY.isNotBlank()

private val authModule = module {
    if (isSyncConfigured) {
        single<SupabaseClient> {
            createSupabaseClient(
                supabaseUrl = SupabaseConfig.URL,
                supabaseKey = SupabaseConfig.ANON_KEY,
            ) {
                install(Auth) {
                    // Custom-scheme deep link for the OAuth redirect (Android/iOS).
                    scheme = "com.shah.cashwise"
                    host = "auth-callback"
                }
            }
        }
        single<AuthRepository> { AuthRepositoryImpl(get()) }
    } else {
        single<AuthRepository> { OfflineAuthRepository() }
    }
    viewModelOf(::SignInViewModel)
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
    authModule,
    appModule,
    onboardingModule,
    setupModule,
    setPinModule,
)

/**
 * Starts the global Koin container. Called once from each platform entry point
 * (Android Application, JVM main, iOS MainViewController) so both Compose and
 * platform code (e.g. the OAuth deep-link handler) share one container.
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModules)
}
