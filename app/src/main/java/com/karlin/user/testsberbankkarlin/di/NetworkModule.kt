package com.karlin.user.testsberbankkarlin.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.karlin.user.testsberbankkarlin.BuildConfig
import com.karlin.user.common.util.Logger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.testsberbankkarlin.di
 */

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder().setLenient()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger { message ->
                        Logger.api(message + "")
                    })
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        return okHttpClientBuilder.build()
    }
}