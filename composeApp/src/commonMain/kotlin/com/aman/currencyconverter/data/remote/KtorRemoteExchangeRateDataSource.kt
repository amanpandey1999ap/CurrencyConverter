package com.aman.currencyconverter.data.remote

import com.aman.core.data.safeCall
import com.aman.core.domain.DataError
import com.aman.core.domain.Result
import com.aman.currencyconverter.data.dto.ExchangeRateDTO
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
