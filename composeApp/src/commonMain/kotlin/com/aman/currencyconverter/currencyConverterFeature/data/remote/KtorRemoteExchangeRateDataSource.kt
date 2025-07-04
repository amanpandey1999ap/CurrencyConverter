package com.aman.currencyconverter.currencyConverterFeature.data.remote

import com.aman.currencyconverter.core.data.safeCall
import com.aman.currencyconverter.core.domain.DataError
import com.aman.currencyconverter.core.domain.Result
import com.aman.currencyconverter.currencyConverterFeature.data.dto.ExchangeRateDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://open.er-api.com"

class KtorRemoteExchangeRateDataSource(
    private val httpClient: HttpClient
) : RemoteExchangeRateDataSource {
    override suspend fun fetchExchangeRates(): Result<ExchangeRateDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/v6/latest/USD"
            ) {

            }
        }
    }
}
