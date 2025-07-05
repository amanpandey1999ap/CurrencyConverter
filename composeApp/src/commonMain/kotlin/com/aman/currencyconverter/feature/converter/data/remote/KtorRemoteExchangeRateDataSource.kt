package com.aman.currencyconverter.feature.converter.data.remote

import com.aman.currencyconverter.core.network.safeCall
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.utils.Constants.BASE_URL
import com.aman.currencyconverter.feature.converter.data.dto.ExchangeRateDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteExchangeRateDataSource(
    private val httpClient: HttpClient
) : RemoteExchangeRateDataSource {
    override suspend fun fetchExchangeRates(): AppResult<ExchangeRateDTO, DataFetchAppError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/v6/latest/USD"
            )
        }
    }
}
