package com.karlin.user.testsberbankkarlin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karlin.user.feature_currencyconverter.presentation.CurrencyConverterFragment
import com.karlin.user.testsberbankkarlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer, CurrencyConverterFragment())
                .commit()
        }

    }
}
