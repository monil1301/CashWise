package com.shah.cashwise.di

import com.shah.cashwise.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Monil on 21/05/25.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API.BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .addInterceptor(provideHeaderInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
     fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): okhttp3.Interceptor {
        return okhttp3.Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader(Constants.API.HEADERS.ACCEPT, Constants.API.HEADERS.APPLICATION_JSON)
                .addHeader(
                    Constants.API.HEADERS.CONTENT_TYPE,
                    Constants.API.HEADERS.APPLICATION_JSON
                )
                // Uncomment this block for token support:
                // UserHelper.accessToken?.let { token ->
                //     addHeader(Constants.API.HEADERS.AUTHORIZATION, "Bearer $token")
                // }
                .build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}