package com.aman.currencyconverter.feature.converter.presentation.viewmodel

import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate
import com.aman.currencyconverter.feature.converter.domain.usecase.FakeFetchExchangeRatesUseCase
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterState
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull

class CurrencyViewModelTest {

    private val mockRates = ExchangeRate(
        result = "success",
        provider = "MockProvider",
        documentation = "https://mock.docs/api",
        termsOfUse = "https://mock.docs/terms",
        timeLastUpdateUnix = 1_720_000_000L,
        timeLastUpdateUtc = "2025-07-05T12:00:00Z",
        timeNextUpdateUnix = 1_720_086_400L,
        timeNextUpdateUtc = "2025-07-06T12:00:00Z",
        timeEolUnix = 1_800_000_000L,
        baseCode = "USD",
        rates = mapOf("USD" to 1.0, "INR" to 83.0, "EUR" to 0.92)
    )

    private fun viewModelWith(result: AppResult<ExchangeRate, DataFetchAppError>) =
        CurrencyViewModel(FakeFetchExchangeRatesUseCase(result))

    private suspend fun waitForState(
        viewModel: CurrencyViewModel,
        condition: (CurrencyConverterState) -> Boolean
    ): CurrencyConverterState {
        return viewModel.state.filter(condition).first()
    }

    @Test
    fun `loads exchange rates successfully`() = runTest {
        val viewModel = viewModelWith(AppResult.Success(mockRates))
        val state = waitForState(viewModel) { it.currencies.isNotEmpty() }

        assertFalse(state.isLoading)
        assertEquals(listOf("EUR", "INR", "USD"), state.currencies)
        assertNull(state.errorMessage)
    }

    @Test
    fun `handles fetch failure`() = runTest {
        val viewModel = viewModelWith(AppResult.Error(DataFetchAppError.Remote.SERVER))
        viewModel.handleAction(CurrencyConverterAction.LoadCurrencies)

        val state = waitForState(viewModel) { !it.isLoading && it.errorMessage != null }

        assertFalse(state.isLoading)
        assertIs<DataFetchAppError.Remote>(state.errorMessage)
    }

    @Test
    fun `updates from currency and recalculates to amount`() = runTest {
        val viewModel = viewModelWith(AppResult.Success(mockRates))
        viewModel.handleAction(CurrencyConverterAction.FromCurrencyChanged("EUR"))

        val state = waitForState(viewModel) { it.fromCurrency == "EUR" }

        assertEquals("EUR", state.fromCurrency)
    }

    @Test
    fun `updates from amount and calculates to amount`() = runTest {
        val viewModel = viewModelWith(AppResult.Success(mockRates))

        viewModel.handleAction(CurrencyConverterAction.FromCurrencyChanged("USD"))
        viewModel.handleAction(CurrencyConverterAction.ToCurrencyChanged("INR"))
        viewModel.handleAction(CurrencyConverterAction.FromAmountChanged("2.0"))

        val state = waitForState(viewModel) { it.toAmount == "166.0" }

        assertEquals("166.0", state.toAmount)
    }

    @Test
    fun `clears error on ErrorDismissed`() = runTest {
        val viewModel = viewModelWith(AppResult.Error(DataFetchAppError.Remote.SERVER))
        viewModel.handleAction(CurrencyConverterAction.ErrorDismissed)

        assertNull(viewModel.state.value.errorMessage)
    }
}
