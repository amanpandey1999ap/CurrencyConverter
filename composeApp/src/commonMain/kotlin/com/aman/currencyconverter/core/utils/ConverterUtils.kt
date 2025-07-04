package com.aman.currencyconverter.core.utils

import kotlin.math.pow


fun recalculateConvertedAmount(
    sourceCurrency: String,
    targetCurrency: String,
    sourceAmount: String,
    rates: Map<String, Double>?,
    onResult: (String) -> Unit
) {
    if (rates == null) return
    val sourceRate = rates[sourceCurrency] ?: return
    val targetRate = rates[targetCurrency] ?: return

    if (sourceAmount.isBlank()) {
        onResult("0.0")
        return
    }

    val amount = sourceAmount.toDoubleOrNull() ?: return
    val usd = amount / sourceRate
    val converted = usd * targetRate

    val formatted = formatToScientificIfLarge(converted, originalInput = sourceAmount)
    onResult(formatted)
}

fun formatToScientificIfLarge(value: Double, originalInput: String, maxDecimals: Int = 4): String {
    if (value == 0.0) return "0.0"

    return if (originalInput.length >= 6) {
        val exponent = kotlin.math.floor(kotlin.math.log10(kotlin.math.abs(value))).toInt()
        val mantissa = value / 10.0.pow(exponent)

        // Round mantissa to required decimal places
        val multiplier = 10.0.pow(maxDecimals)
        val roundedMantissa = kotlin.math.round(mantissa * multiplier) / multiplier

        "${roundedMantissa}e${if (exponent >= 0) "+" else ""}$exponent"
    } else {
        val rounded = kotlin.math.round(value * 100) / 100
        rounded.toString()
    }
}
