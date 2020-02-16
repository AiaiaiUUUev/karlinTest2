package com.karlin.user.feature_currencyconverter.business

import com.karlin.user.common.functional.Either
import com.karlin.user.feature_currencyconverter.data.CurrenciesRepository
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import io.reactivex.Single
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter.business
 */

class GetCurrenciesInteractor @Inject constructor(private val repository: CurrenciesRepository) {
    fun getCurrencies(base: String?): Single<Either<CurrencyFailure, CurrencyEntity>> {
        return repository.getCurrencies(base)
    }
}