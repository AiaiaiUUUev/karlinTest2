package com.karlin.user.feature_currencyconverter.business

import com.karlin.user.common.functional.Either
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject
import kotlin.math.roundToInt

/**
// Created by Karlin Dmitriy on 17.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.business
 */

class CalculateCurrencyInteractor @Inject constructor() {

    fun execute(enteredValue: Double?, target: Double?): Either<CurrencyFailure, Double> {
        return if (enteredValue != null && target != null) {
            val calculatedValue = enteredValue * BigDecimal(target).setScale(3, RoundingMode.HALF_UP).toDouble()
            Either.Right(calculatedValue)
        } else {
            Either.Left(CurrencyFailure.ExceptionOnCalculate)
        }
    }
}