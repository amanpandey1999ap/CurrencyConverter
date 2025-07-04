package com.aman.currencyconverter.feature.converter.presentation.model

import com.aman.currencyconverter.core.result.DataFetchAppError

data class CurrencyConverterState(
    val isLoading: Boolean = false,
    val currencies: List<String> = emptyList(),
    val fromCurrency: String = "USD",
    val toCurrency: String = "INR",
    val fromAmount: String = "0.0",
    val toAmount: String = "0.0",
    val errorMessage: DataFetchAppError? = null
)
