package com.aman.currencyconverter

import androidx.compose.ui.window.ComposeUIViewController
import com.aman.currencyconverter.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }