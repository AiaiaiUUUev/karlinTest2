package com.karlin.user.feature_currencyconverter.di

import android.content.Context
import com.google.gson.Gson
import com.karlin.user.common.FeatureDependencies
import okhttp3.OkHttpClient

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter.di
 */

interface FeatureCurrencyConverterDepedencies : FeatureDependencies {
    val okHttpClient: OkHttpClient
    val gson: Gson
    val context: Context
}