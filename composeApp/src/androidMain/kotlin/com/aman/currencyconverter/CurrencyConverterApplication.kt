package com.aman.currencyconverter

import android.app.Application
import com.aman.currencyconverter.di.initKoin
import org.koin.android.ext.koin.androidContext

class CurrencyConverterApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CurrencyConverterApplication)
        }
    }
}