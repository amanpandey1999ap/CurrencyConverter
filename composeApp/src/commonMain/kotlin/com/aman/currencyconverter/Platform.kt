package com.aman.currencyconverter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform