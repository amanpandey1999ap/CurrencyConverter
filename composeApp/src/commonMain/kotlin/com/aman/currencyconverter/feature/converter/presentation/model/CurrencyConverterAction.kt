package com.aman.currencyconverter.feature.converter.presentation.model

sealed class CurrencyConverterAction {
    data class FromCurrencyChanged(val currency: String) : CurrencyConverterAction()
    data class ToCurrencyChanged(val currency: String) : CurrencyConverterAction()
    data class FromAmountChanged(val amount: String) : CurrencyConverterAction()
    data class ToAmountChanged(val amount: String) : CurrencyConverterAction()
    data object LoadCurrencies : CurrencyConverterAction()
    data object SwapCurrencies : CurrencyConverterAction()
}
