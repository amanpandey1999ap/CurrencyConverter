package com.aman.currencyconverter.core.network

import com.aman.currencyconverter.core.result.DataFetchAppError
import com.aman.currencyconverter.core.result.AppResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): AppResult<T, DataFetchAppError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return AppResult.Error(DataFetchAppError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        return AppResult.Error(DataFetchAppError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        // Ensure coroutine hasn't been cancelled; rethrow CancellationException if needed
        coroutineContext.ensureActive()
        return AppResult.Error(DataFetchAppError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): AppResult<T, DataFetchAppError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                AppResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                AppResult.Error(DataFetchAppError.Remote.SERIALIZATION)
            }
        }
        408 -> AppResult.Error(DataFetchAppError.Remote.REQUEST_TIMEOUT)
        429 -> AppResult.Error(DataFetchAppError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> AppResult.Error(DataFetchAppError.Remote.SERVER)
        else -> AppResult.Error(DataFetchAppError.Remote.UNKNOWN)
    }
}
