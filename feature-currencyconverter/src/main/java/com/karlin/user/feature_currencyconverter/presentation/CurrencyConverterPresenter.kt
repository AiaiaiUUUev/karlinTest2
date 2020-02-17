package com.karlin.user.feature_currencyconverter.presentation

import com.karlin.user.feature_currencyconverter.business.CalculateCurrencyInteractor
import com.karlin.user.feature_currencyconverter.business.GetCurrenciesInteractor
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feature_currencyconverter.presentation
 */

class CurrencyConverterPresenter @Inject constructor(
    private val getCurrenciesInteractor: GetCurrenciesInteractor,
    private val calculateCurrencyInteractor: CalculateCurrencyInteractor
) {

    private lateinit var view: CurrencyConverterFragmentView
    private val disposable = CompositeDisposable()

    fun setView(view: CurrencyConverterFragmentView) {
        this.view = view
    }

    fun loadCurrencies(base: String? = null) {
        disposable.add(
            getCurrenciesInteractor.getCurrencies(base)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showProgressBar() }
                .doFinally { view.hideProgressBar(); view.hideSwipeRefresh() }
                .subscribe(
                    { it.either(view::handleError, ::handleGetCurrencies) },
                    { t -> view.handleError(CurrencyFailure.ExceptionCannotLoadData) }
                )
        )
    }

    private fun handleGetCurrencies(currencyEntity: CurrencyEntity) {
        view.setCurrencies(currencyEntity)
    }

    fun calculateCurrency(enteredValue: Double?, target: Double?) {
        val result = calculateCurrencyInteractor.execute(enteredValue, target)
        result.either(view::handleError, view::showCalculatedValue)
    }

    fun onDestroy() = disposable.clear()

}