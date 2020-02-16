package com.karlin.user.feature_currencyconverter.di

import com.karlin.user.feature_currencyconverter.presentation.CurrencyConverterFragment
import dagger.Component
import javax.inject.Singleton

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter.di
 */

@Singleton
@Component(
    modules = [
        CurrencyConverterModule::class,
        CurrencyRepositoryModule::class
    ],
    dependencies = [FeatureCurrencyConverterDepedencies::class]
)
interface CurrencyConverterComponent {
    fun inject(fragment: CurrencyConverterFragment)
}