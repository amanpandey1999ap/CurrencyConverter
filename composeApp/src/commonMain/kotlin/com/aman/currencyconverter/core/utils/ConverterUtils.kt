package com.aman.currencyconverter.core.utils

import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round


data class ConversionInput(
    val sourceCurrency: String,
    val targetCurrency: String,
    val amount: String,
    val rates: Map<String, Double>?
)

/**
 * Recalculates and returns the converted currency amount as a formatted string.
 */
fun convertAmount(input: ConversionInput): String {
    val rates = input.rates ?: return "0.0"

    val fromRate = rates[input.sourceCurrency] ?: return "0.0"
    val toRate = rates[input.targetCurrency] ?: return "0.0"

    val amount = input.amount.toDoubleOrNull() ?: return "0.0"

    // Convert from source currency → USD → target currency
    val usd = amount / fromRate
    val converted = usd * toRate

    return formatNicely(converted, input.amount)
}


/**
 * Formats the value:
 * - Uses scientific format if input is 6+ digits
 * - Otherwise rounds to 2 decimals
 */
private fun formatNicely(value: Double, originalInput: String): String {
    if (value == 0.0) return "0.0"

    return if (originalInput.length >= 6) {
        val exponent = log10(abs(value)).toInt()
        val base = value / 10.0.pow(exponent)
        val roundedBase = (round(base * 10000) / 10000)
        "${roundedBase}e${if (exponent >= 0) "+" else ""}$exponent"
    } else {
        val rounded = (round(value * 100) / 100)
        rounded.toString()
    }
}
