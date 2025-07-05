package com.aman.currencyconverter.feature.converter.data.repository

import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.map
import com.aman.currencyconverter.feature.converter.data.mappers.toExchangeRate
import com.aman.currencyconverter.feature.converter.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate
import com.aman.currencyconverter.feature.converter.domain.repository.CurrencyExchangeRepository

class DefaultCurrencyExchangeRepository(
    private val remoteExchangeRateDataSource: RemoteExchangeRateDataSource
): CurrencyExchangeRepository {
    override suspend fun fetchExchangeRates(): AppResult<ExchangeRate, DataFetchAppError.Remote> {
        return remoteExchangeRateDataSource.fetchExchangeRates().map { dto -> dto.toExchangeRate() }
    }
}
