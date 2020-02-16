package com.karlin.user.feature_currencyconverter.presentation

import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure

interface CurrencyConverterFragmentView {
    fun setCurrencies(currencyEntity: CurrencyEntity)
    fun showProgressBar()
    fun hideProgressBar()
    fun showErrorDialog(error: CurrencyFailure)
    fun showCalculatedValue(calculatedValue: Double)
    fun hideSwipeRefresh()
}