package com.aman.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aman.currencyconverter.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.presentation.model.CurrencyConverterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CurrencyViewModel : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state.asStateFlow()

    fun handleAction(action: CurrencyConverterAction) {
        when (action) {
            is CurrencyConverterAction.FromCurrencyChanged -> {
                _state.update { it.copy(fromCurrency = action.currency) }
            }

            is CurrencyConverterAction.ToCurrencyChanged -> {
                _state.update { it.copy(toCurrency = action.currency) }
            }

            is CurrencyConverterAction.AmountChanged -> {
                _state.update { it.copy(amount = action.amount) }
            }

            is CurrencyConverterAction.LoadCurrencies -> {
                //loadRates()
            }

            is CurrencyConverterAction.Convert -> {
                //convertAmount()
            }
        }
    }

}
