package com.aman.currencyconverter.feature.converter.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.aman.currencyconverter.core.result.onError
import com.aman.currencyconverter.core.result.onSuccess
import com.aman.currencyconverter.core.utils.ConversionInput
import com.aman.currencyconverter.core.utils.convertAmount
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate
import com.aman.currencyconverter.feature.converter.domain.repository.CurrencyExchangeRepository
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterState
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

    private var cachedRates: ExchangeRate? = null

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state: StateFlow<CurrencyConverterState> = _state
        .onStart { loadRates() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value)

    fun handleAction(action: CurrencyConverterAction) {
        when (action) {
            is CurrencyConverterAction.FromCurrencyChanged -> {
                _state.update { it.copy(fromCurrency = action.currency) }
                recalculateToAmount()
            }

            is CurrencyConverterAction.ToCurrencyChanged -> {
                _state.update { it.copy(toCurrency = action.currency) }
                recalculateToAmount()
            }

            is CurrencyConverterAction.FromAmountChanged -> {
                _state.update { it.copy(fromAmount = action.amount) }
                recalculateToAmount()
            }

            is CurrencyConverterAction.ToAmountChanged -> {
                _state.update { it.copy(toAmount = action.amount) }
                recalculateFromAmount()
            }

            is CurrencyConverterAction.SwapCurrencies -> {
                _state.update {
                    it.copy(
                        fromCurrency = it.toCurrency,
                        toCurrency = it.fromCurrency,
                        fromAmount = it.toAmount,
                        toAmount = it.fromAmount
                    )
                }
            }

            is CurrencyConverterAction.LoadCurrencies -> loadRates()
        }
    }

    private fun loadRates() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        repository.fetchExchangeRates()
            .onSuccess { response ->
                cachedRates = response
                _state.update {
                    it.copy(
                        currencies = response.rates.keys.sorted(),
                        isLoading = false,
                        errorMessage = null
                    )
                }
                recalculateToAmount()
            }
            .onError { error ->
                _state.update {
                    it.copy(isLoading = false, errorMessage = "Failed to load rates: $error")
                }
            }
    }

    private fun recalculateToAmount() {
        val state = _state.value
        val result = convertAmount(
            ConversionInput(
                sourceCurrency = state.fromCurrency,
                targetCurrency = state.toCurrency,
                amount = state.fromAmount,
                rates = cachedRates?.rates
            )
        )
        _state.update { it.copy(toAmount = result) }
    }

    private fun recalculateFromAmount() {
        val state = _state.value
        val result = convertAmount(
            ConversionInput(
                sourceCurrency = state.toCurrency,
                targetCurrency = state.fromCurrency,
                amount = state.toAmount,
                rates = cachedRates?.rates
            )
        )
        _state.update { it.copy(fromAmount = result) }
    }

}
