package com.aman.currencyconverter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aman.core.domain.onError
import com.aman.core.domain.onSuccess
import com.aman.currencyconverter.domain.repository.CurrencyExchangeRepository
import com.aman.currencyconverter.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.presentation.model.CurrencyConverterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val repository: CurrencyExchangeRepository
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state
        .onStart {
            loadRates()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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
                loadRates()
            }

            is CurrencyConverterAction.Convert -> {
                //convertAmount()
            }
        }
    }

    private fun loadRates() = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            repository.fetchExchangeRates()
                .onSuccess { response ->
                    _state.update {
                        it.copy(
                            currencies = response.rates.keys.toList(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to load rates: $error"
                        )
                    }
                }
        }

}
