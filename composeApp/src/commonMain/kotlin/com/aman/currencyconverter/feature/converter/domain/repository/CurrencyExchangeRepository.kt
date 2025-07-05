package com.aman.currencyconverter.feature.converter.domain.repository

import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate

interface CurrencyExchangeRepository {
    suspend fun fetchExchangeRates(): AppResult<ExchangeRate, DataFetchAppError>
}
