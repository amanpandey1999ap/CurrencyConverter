package com.aman.currencyconverter.data.mappers

import com.aman.currencyconverter.data.dto.ExchangeRateDTO
import com.aman.currencyconverter.domain.model.ExchangeRate

fun ExchangeRateDTO.toExchangeRate(): ExchangeRate {
    return ExchangeRate(
        result = result,
        provider = provider,
        documentation = documentation,
        termsOfUse = termsOfUse,
        timeLastUpdateUnix = timeLastUpdateUnix,
        timeLastUpdateUtc = timeLastUpdateUtc,
        timeNextUpdateUnix = timeNextUpdateUnix,
        timeNextUpdateUtc = timeNextUpdateUtc,
        timeEolUnix = timeEolUnix,
        baseCode = baseCode,
        rates = rates
    )
}