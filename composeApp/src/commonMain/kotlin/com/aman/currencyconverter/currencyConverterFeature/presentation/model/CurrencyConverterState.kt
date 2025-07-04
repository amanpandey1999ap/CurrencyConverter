package com.aman.currencyconverter.currencyConverterFeature.presentation.model

data class CurrencyConverterState(
    val isLoading: Boolean = false,
    val currencies: List<String> = emptyList(),
    val fromCurrency: String = "USD",
    val toCurrency: String = "INR",
    val amount: String = "",
    val convertedAmount: String? = null,
    val errorMessage: String? = null
)
