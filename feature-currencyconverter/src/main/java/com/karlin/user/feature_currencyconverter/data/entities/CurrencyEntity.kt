package com.karlin.user.feature_currencyconverter.data.entities

import androidx.collection.ArrayMap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.karlin.user.feature_currencyconverter.data.db.MapConverter

@Entity(tableName = "currencies")
@TypeConverters(MapConverter::class)
data class CurrencyEntity(
    @PrimaryKey @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: ArrayMap<String, Double>
)