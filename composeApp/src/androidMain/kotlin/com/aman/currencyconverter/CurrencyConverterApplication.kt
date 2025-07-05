package com.aman.currencyconverter

import android.app.Application
import com.aman.currencyconverter.di.initKoin
import org.koin.android.ext.koin.androidContext

class CurrencyConverterApplication: Application() {

    companion object {
        var instance: CurrencyConverterApplication? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin {
            androidContext(this@CurrencyConverterApplication)
        }
    }
}