package com.karlin.user.feature_currencyconverter.data

import com.karlin.user.common.functional.Either
import com.karlin.user.data.db.CurrenciesDaoImpl
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import io.reactivex.Single
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter.data
 */

class CurrenciesLocalDataSource @Inject constructor(private val dao: CurrenciesDaoImpl) {

    fun getCurrencies(base: String): Single<Either<CurrencyFailure, CurrencyEntity>> {
        return dao.getCurrencies(base).map { Either.Right(it) }
    }
}