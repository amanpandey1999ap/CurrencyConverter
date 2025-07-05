package com.aman.currencyconverter.feature.converter.domain.usecase

import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate
import com.aman.currencyconverter.feature.converter.domain.repository.CurrencyExchangeRepository

class DefaultFetchExchangeRatesUseCase(
    private val repository: CurrencyExchangeRepository
) : FetchExchangeRatesUseCase {
    override suspend fun invoke(): AppResult<ExchangeRate, DataFetchAppError> {
        return repository.fetchExchangeRates()
    }
}
