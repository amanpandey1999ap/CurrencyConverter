package com.aman.currencyconverter.currencyConverterFeature.domain.repository

import com.aman.currencyconverter.core.domain.DataError
import com.aman.currencyconverter.core.domain.Result
import com.aman.currencyconverter.currencyConverterFeature.domain.model.ExchangeRate

interface CurrencyExchangeRepository {
    suspend fun fetchExchangeRates(): Result<ExchangeRate, DataError.Remote>
}
