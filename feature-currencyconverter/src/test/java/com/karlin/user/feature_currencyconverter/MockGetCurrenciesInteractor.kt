package com.karlin.user.feature_currencyconverter

import androidx.collection.ArrayMap
import androidx.room.EmptyResultSetException
import com.karlin.user.common.functional.Either
import com.karlin.user.common.util.NetworkHandler
import com.karlin.user.common.util.stubRxJavaSchedulers
import com.karlin.user.feature_currencyconverter.business.GetCurrenciesInteractor
import com.karlin.user.feature_currencyconverter.data.CurrenciesLocalDataSource
import com.karlin.user.feature_currencyconverter.data.CurrenciesRemoteDataSource
import com.karlin.user.feature_currencyconverter.data.CurrenciesRepository
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import com.karlin.user.feature_currencyconverter.exception.CurrencyFailure
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MockGetCurrenciesInteractor {

    @Before
    fun prepare() = stubRxJavaSchedulers()

    @Test
    fun successCase() {
        val rub = "RUB"
        val networkhandler: NetworkHandler = mock {
            on { isConnected }.thenReturn(true)
        }
        val currenciesLocalDataSource: CurrenciesLocalDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.never())
        }

        val currencyEntity = CurrencyEntity(rub, ArrayMap())
        val expectedResponse = Either.Right(currencyEntity)
        val currenciesRemoteDataSource: CurrenciesRemoteDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.just(expectedResponse))
        }
        val repository = CurrenciesRepository.Impl(
            networkhandler,
            currenciesLocalDataSource,
            currenciesRemoteDataSource
        )
        val interactor = GetCurrenciesInteractor(repository)

        val subscriber = interactor.getCurrencies(rub).test()
        subscriber.assertValues(expectedResponse)
    }

    @Test
    fun unexpectedErrorCase() {
        val rub = "RUB"
        val networkhandler: NetworkHandler = mock {
            on { isConnected }.thenReturn(false)
        }
        val emptyResultSetException = EmptyResultSetException("")
        val currenciesLocalDataSource: CurrenciesLocalDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.error(emptyResultSetException))
        }

        val currenciesRemoteDataSource: CurrenciesRemoteDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.never())
        }
        val repository = CurrenciesRepository.Impl(
            networkhandler,
            currenciesLocalDataSource,
            currenciesRemoteDataSource
        )
        val interactor = GetCurrenciesInteractor(repository)

        val subscriber = interactor.getCurrencies(rub).test()
        subscriber.assertError { t -> t == emptyResultSetException }
    }

    @Test
    fun successCaseWithoutNetwork() {
        val rub = "RUB"
        val networkhandler: NetworkHandler = mock {
            on { isConnected }.thenReturn(false)
        }

        val currencyEntity = CurrencyEntity(rub, ArrayMap())

        val currenciesLocalDataSource: CurrenciesLocalDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.just(currencyEntity))
        }
        val currenciesRemoteDataSource: CurrenciesRemoteDataSource = mock {
            on { getCurrencies(rub) }.thenReturn(Single.never())
        }
        val repository = CurrenciesRepository.Impl(
            networkhandler,
            currenciesLocalDataSource,
            currenciesRemoteDataSource
        )

        val interactor = GetCurrenciesInteractor(repository)

        val subscriber = interactor.getCurrencies(rub).test()
        subscriber.assertValue { r ->
            r.getResponse()
                .let {
                    it is CurrencyFailure.ExceptionNetworkConnection
                            && it.currencyEntity == currencyEntity
                }
        }
    }
}