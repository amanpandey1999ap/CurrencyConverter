package com.aman.currencyconverter.feature.converter.domain.usecase

import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate

interface FetchExchangeRatesUseCase {
    suspend operator fun invoke(): AppResult<ExchangeRate, DataFetchAppError>
}