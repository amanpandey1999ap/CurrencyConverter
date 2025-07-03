package com.aman.currencyconverter.data.repository

import com.aman.core.domain.DataError
import com.aman.core.domain.Result
import com.aman.core.domain.map
import com.aman.currencyconverter.data.mappers.toExchangeRate
import com.aman.currencyconverter.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.domain.model.ExchangeRate
import com.aman.currencyconverter.domain.repository.CurrencyExchangeRepository

class DefaultCurrencyExchangeRepository(
    private val remoteExchangeRateDataSource: RemoteExchangeRateDataSource
): CurrencyExchangeRepository {
    override suspend fun fetchExchangeRates(): Result<ExchangeRate, DataError.Remote> {
        return remoteExchangeRateDataSource.fetchExchangeRates().map { dto -> dto.toExchangeRate() }
    }
}
