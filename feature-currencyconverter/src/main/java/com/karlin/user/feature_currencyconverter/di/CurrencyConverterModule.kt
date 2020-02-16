package com.karlin.user.feature_currencyconverter.di

import android.content.Context
import com.google.gson.Gson
import com.karlin.user.data.db.createCurrenciesDao
import com.karlin.user.feature_currencyconverter.data.CurrenciesRepository
import com.karlin.user.feature_currencyconverter.data.CurrencyApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter.di
 */

@Module
object CurrencyConverterModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CurrencyApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(context: Context) = createCurrenciesDao(context)

}