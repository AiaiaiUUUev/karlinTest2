package com.karlin.user.feature_currencyconverter.presentation

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.karlin.user.common.extension.hide
import com.karlin.user.common.extension.show
import com.karlin.user.common.findFeatureDependencies
import com.karlin.user.feature_currencyconverter.R
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.di.DaggerCurrencyConverterComponent
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import kotlinx.android.synthetic.main.fragment_currency_converter.*
import javax.inject.Inject


/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.feture_currencyconverter
 */

class CurrencyConverterFragment : Fragment(), CurrencyConverterFragmentView {

    @Inject
    lateinit var presenter: CurrencyConverterPresenter
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var currencyEntity: CurrencyEntity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initDi()
        presenter.setView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        presenter.loadCurrencies()
    }

    private fun initUi() {
        swipeRefresh.setOnRefreshListener {
            if (::currencyEntity.isInitialized) {
                presenter.loadCurrencies(currencyEntity.base)
            } else {
                presenter.loadCurrencies()
            }
        }

        btnCalculate.setOnClickListener {
            etEnterCurrency.text.run {
                if (isNotBlank()) {
                    val enteredValue = toString().toDouble()
                    val target = currencyEntity.rates[spinnerTargetCurrency.selectedItem]
                    presenter.calculateCurrency(enteredValue, target)
                } else {
                    displayDialog(getString(R.string.please_enter_the_value))
                }
            }
        }

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInitialCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(adapterView: AdapterView<*>?) = Unit

                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    pos: Int,
                    long: Long
                ) {
                    presenter.loadCurrencies(spinnerAdapter.getItem(pos))
                }

            }
        spinnerInitialCurrency.adapter = spinnerAdapter
        spinnerTargetCurrency.adapter = spinnerAdapter
    }

    override fun setCurrencies(currencyEntity: CurrencyEntity) {
        hideSwipeRefresh()
        this.currencyEntity = currencyEntity
        spinnerAdapter.addAll(currencyEntity.rates.keys)
        spinnerAdapter.notifyDataSetChanged()
        spinnerInitialCurrency.setSelection(currencyEntity.rates.indexOfKey(currencyEntity.base))
    }

    override fun showCalculatedValue(calculatedValue: Double) {
        tvConvertedSum.text = calculatedValue.toString()
    }

    override fun showProgressBar() = progressBar.show()
    override fun hideProgressBar() = progressBar.hide()
    override fun hideSwipeRefresh() {
        swipeRefresh.isRefreshing = false
    }

    override fun handleError(error: CurrencyFailure) {
        when (error) {
            CurrencyFailure.ExceptionOnLoading -> displayDialog(getString(R.string.exception_occured_when_loading))
            is CurrencyFailure.ExceptionNetworkConnection -> {
                error.currencyEntity?.let { setCurrencies(it) }
                displayDialog(getString(R.string.no_internet_connection))
            }
            is CurrencyFailure.ExceptionCannotLoadData -> displayDialog(getString(R.string.cannot_load_data))
        }
    }

    private fun displayDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(message)
            .setPositiveButton(getString(R.string.ok)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }

    private fun initDi() {
        DaggerCurrencyConverterComponent
            .builder()
            .featureCurrencyConverterDepedencies(findFeatureDependencies())
            .build()
            .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}