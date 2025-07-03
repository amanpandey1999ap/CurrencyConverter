package com.aman.currencyconverter.domain.repository

import com.aman.core.domain.DataError
import com.aman.core.domain.Result
import com.aman.currencyconverter.domain.model.ExchangeRate

interface CurrencyExchangeRepository {
    suspend fun fetchExchangeRates(): Result<ExchangeRate, DataError.Remote>
}
