package com.karlin.user.feature_currencyconverter.exception

import com.karlin.user.common.exception.Failure

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.exception
 */

sealed class CurrencyFailure : Failure.FeatureFailure() {
    object ExceptionOnLoad : CurrencyFailure()
    object ExceptionOnCalculate : CurrencyFailure()
}