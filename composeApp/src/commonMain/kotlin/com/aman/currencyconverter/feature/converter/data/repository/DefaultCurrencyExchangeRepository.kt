package com.aman.currencyconverter.feature.converter.data.repository

import androidx.datastore.core.IOException
import com.aman.currencyconverter.core.local.StoreManager
import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import com.aman.currencyconverter.core.result.map
import com.aman.currencyconverter.core.utils.Constants.CACHE_KEY
import com.aman.currencyconverter.core.utils.Constants.DEFAULT_DATASTORE_STRING
import com.aman.currencyconverter.feature.converter.data.dto.ExchangeRateDTO
import com.aman.currencyconverter.feature.converter.data.mappers.toExchangeRate
import com.aman.currencyconverter.feature.converter.data.remote.RemoteExchangeRateDataSource
import com.aman.currencyconverter.feature.converter.domain.model.ExchangeRate
import com.aman.currencyconverter.feature.converter.domain.repository.CurrencyExchangeRepository
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DefaultCurrencyExchangeRepository(
    private val remoteExchangeRateDataSource: RemoteExchangeRateDataSource,
    private val storeManager: StoreManager
) : CurrencyExchangeRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun fetchExchangeRates(): AppResult<ExchangeRate, DataFetchAppError> {
        val currentTimeUnix = Clock.System.now().epochSeconds

        // Try to fetch cached exchange rates
        val cachedJson = storeManager.getValue(CACHE_KEY)
        if (cachedJson != DEFAULT_DATASTORE_STRING) {
            try {
                val cachedDto = Json.decodeFromString(ExchangeRateDTO.serializer(), cachedJson)

                // Use cache if it's still valid (based on time_next_update_unix)
                if (currentTimeUnix < cachedDto.timeNextUpdateUnix) {
                    val exchangeRate = cachedDto.toExchangeRate()
                    return AppResult.Success(exchangeRate)
                }

            } catch (e: SerializationException) {
                // JSON was malformed or incompatible
                return AppResult.Error(DataFetchAppError.Local.UNKNOWN)

            } catch (e: IOException) {
                // IO error while reading cache
                return AppResult.Error(DataFetchAppError.Local.DISK_FULL)
            }
        }

        // Fallback: Fetch fresh data from remote
        return remoteExchangeRateDataSource.fetchExchangeRates().map { dto ->
            try {
                // Cache the newly fetched data
                val json = Json.encodeToString(ExchangeRateDTO.serializer(), dto)
                storeManager.save(CACHE_KEY, json)

            } catch (e: SerializationException) {
                // If writing to cache fails due to serialization issue
                return AppResult.Error(DataFetchAppError.Local.UNKNOWN)

            } catch (e: IOException) {
                // Disk write error
                return AppResult.Error(DataFetchAppError.Local.DISK_FULL)
            }

            // Return fresh exchange rates
            dto.toExchangeRate()
        }
    }
}
