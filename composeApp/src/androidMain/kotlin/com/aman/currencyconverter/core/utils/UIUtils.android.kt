package com.aman.currencyconverter.core.utils

import android.widget.Toast
import com.aman.currencyconverter.CurrencyConverterApplication

actual fun showMessage(message: String) {

    val mContext = CurrencyConverterApplication.instance
    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
}
