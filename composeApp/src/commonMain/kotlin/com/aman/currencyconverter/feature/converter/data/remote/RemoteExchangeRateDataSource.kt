package com.aman.currencyconverter.feature.converter.data.remote

import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.feature.converter.data.dto.ExchangeRateDTO

interface RemoteExchangeRateDataSource {
    suspend fun fetchExchangeRates(): AppResult<ExchangeRateDTO, DataFetchAppError.Remote>
}
