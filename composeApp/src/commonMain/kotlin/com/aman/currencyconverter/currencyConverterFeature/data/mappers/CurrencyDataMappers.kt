package com.aman.currencyconverter.currencyConverterFeature.data.mappers

import com.aman.currencyconverter.currencyConverterFeature.data.dto.ExchangeRateDTO
import com.aman.currencyconverter.currencyConverterFeature.domain.model.ExchangeRate

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