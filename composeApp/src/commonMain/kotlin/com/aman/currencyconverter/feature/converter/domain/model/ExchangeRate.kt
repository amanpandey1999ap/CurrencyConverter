package com.aman.currencyconverter.feature.converter.domain.model

data class ExchangeRate(
    val result: String,
    val provider: String,
    val documentation: String,
    val termsOfUse: String,
    val timeLastUpdateUnix: Long,
    val timeLastUpdateUtc: String,
    val timeNextUpdateUnix: Long,
    val timeNextUpdateUtc: String,
    val timeEolUnix: Long,
    val baseCode: String,
    val rates: Map<String, Double>
)
