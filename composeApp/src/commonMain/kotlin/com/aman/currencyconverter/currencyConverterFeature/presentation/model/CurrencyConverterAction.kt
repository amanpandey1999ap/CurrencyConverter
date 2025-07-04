package com.aman.currencyconverter.currencyConverterFeature.presentation.model

sealed class CurrencyConverterAction {
    data class FromCurrencyChanged(val currency: String) : CurrencyConverterAction()
    data class ToCurrencyChanged(val currency: String) : CurrencyConverterAction()
    data class AmountChanged(val amount: String) : CurrencyConverterAction()
    data object Convert : CurrencyConverterAction()
    data object LoadCurrencies : CurrencyConverterAction()
}
