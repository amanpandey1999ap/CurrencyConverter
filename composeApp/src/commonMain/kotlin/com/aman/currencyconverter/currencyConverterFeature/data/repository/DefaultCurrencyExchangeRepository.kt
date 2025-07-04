package com.aman.currencyconverter.currencyConverterFeature.data.repository

import com.aman.currencyconverter.core.domain.DataError
import com.aman.currencyconverter.core.domain.Result
import com.aman.currencyconverter.core.domain.map
import com.aman.currencyconverter.currencyConverterFeature.data.mappers.toExchangeRate
import com.aman.currencyconverter.currencyConverterFeature.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.currencyConverterFeature.domain.model.ExchangeRate
import com.aman.currencyconverter.currencyConverterFeature.domain.repository.CurrencyExchangeRepository

class DefaultCurrencyExchangeRepository(
    private val remoteExchangeRateDataSource: RemoteExchangeRateDataSource
): CurrencyExchangeRepository {
    override suspend fun fetchExchangeRates(): Result<ExchangeRate, DataError.Remote> {
        return remoteExchangeRateDataSource.fetchExchangeRates().map { dto -> dto.toExchangeRate() }
    }
}
