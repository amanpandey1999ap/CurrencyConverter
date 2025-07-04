package com.aman.currencyconverter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.aman.currencyconverter.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Currency Converter",
        ) {
            App()
        }
    }
}