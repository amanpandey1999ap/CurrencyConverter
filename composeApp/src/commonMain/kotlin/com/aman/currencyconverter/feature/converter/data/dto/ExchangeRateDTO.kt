package com.aman.currencyconverter.feature.converter.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateDTO(
    val result: String,
    val provider: String,
    val documentation: String,
    @SerialName("terms_of_use")
    val termsOfUse: String,
    @SerialName("time_last_update_unix")
    val timeLastUpdateUnix: Long,
    @SerialName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerialName("time_next_update_unix")
    val timeNextUpdateUnix: Long,
    @SerialName("time_next_update_utc")
    val timeNextUpdateUtc: String,
    @SerialName("time_eol_unix")
    val timeEolUnix: Long,
    @SerialName("base_code")
    val baseCode: String,
    val rates: Map<String, Double>
)
