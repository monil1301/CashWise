package com.shah.cashwise.di

import com.google.firebase.auth.FirebaseAuth
import com.shah.cashwise.data.api.AuthApi
import com.shah.cashwise.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Monil on 21/05/25.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseAuth: FirebaseAuth, authApi: AuthApi): AuthRepository {
        return AuthRepository(firebaseAuth, authApi)
    }
}