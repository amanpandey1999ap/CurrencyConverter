package com.aman.currencyconverter.data.remote

import com.aman.core.domain.DataError
import com.aman.core.domain.Result
import com.aman.currencyconverter.data.dto.ExchangeRateDTO

interface RemoteExchangeRateDataSource {
    suspend fun fetchExchangeRates(): Result<ExchangeRateDTO, DataError.Remote>
}
