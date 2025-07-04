package com.aman.currencyconverter.currencyConverterFeature.data.remote

import com.aman.currencyconverter.core.domain.DataError
import com.aman.currencyconverter.core.domain.Result
import com.aman.currencyconverter.currencyConverterFeature.data.dto.ExchangeRateDTO

interface RemoteExchangeRateDataSource {
    suspend fun fetchExchangeRates(): Result<ExchangeRateDTO, DataError.Remote>
}
