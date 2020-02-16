package com.karlin.user.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karlin.user.feature_currencyconverter.data.entities.CurrencyEntity
import io.reactivex.Single

@Dao
interface CurrenciesDaoImpl {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(currencies: CurrencyEntity)

    @Query("SELECT * FROM currencies WHERE :base == base")
    fun getCurrencies(base: String): Single<CurrencyEntity>
}