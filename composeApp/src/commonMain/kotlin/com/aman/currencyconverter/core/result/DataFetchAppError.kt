package com.aman.currencyconverter.core.result

sealed interface DataFetchAppError: AppError {
    enum class Remote: DataFetchAppError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: DataFetchAppError {
        DISK_FULL,
        UNKNOWN
    }
}
