package com.karlin.user.feature_currencyconverter.data.db

import androidx.collection.ArrayMap
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

internal class MapConverter {
    val gson by lazy(LazyThreadSafetyMode.NONE) { Gson() }

    @TypeConverter
    fun fromMap(map: ArrayMap<String?, Double?>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromString(serializedMap: String?): ArrayMap<String, Double> {
        val type: Type = object : TypeToken<ArrayMap<String?, Double?>?>() {}.type
        return gson.fromJson(serializedMap, type)
    }
}