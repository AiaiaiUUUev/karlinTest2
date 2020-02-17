package com.karlin.user.common.util

import android.util.Log
import com.karlin.user.common.BuildConfig

/**
// Created by Karlin Dmitriy on 16.02.2020.
// PackageName com.karlin.user.testsberbankkarlin.utils
 */

object Logger {
    fun msg(msg: Any) {
        if (BuildConfig.DEBUG) {
            Log.i("MSG", msg.toString() + "")
        }
    }

    fun api(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.i("API", msg)
        }
    }

    fun msg(tag: String?, msg: Any) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg.toString() + "")
        }
    }
}