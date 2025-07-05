package com.aman.currencyconverter.feature.converter.domain.usecase

import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate

class FakeFetchExchangeRatesUseCase(
    private val result: AppResult<ExchangeRate, DataFetchAppError>
) : FetchExchangeRatesUseCase {
    override suspend fun invoke(): AppResult<ExchangeRate, DataFetchAppError> = result
}