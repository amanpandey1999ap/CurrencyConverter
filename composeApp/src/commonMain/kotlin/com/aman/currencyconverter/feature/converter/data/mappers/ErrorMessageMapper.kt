package com.aman.currencyconverter.feature.converter.data.mappers

import androidx.compose.runtime.Composable
import com.aman.currencyconverter.core.result.DataFetchAppError
import currencyconverter.composeapp.generated.resources.Res
import currencyconverter.composeapp.generated.resources.error_disk_full
import currencyconverter.composeapp.generated.resources.error_local_unknown
import currencyconverter.composeapp.generated.resources.error_no_internet
import currencyconverter.composeapp.generated.resources.error_serialization
import currencyconverter.composeapp.generated.resources.error_server
import currencyconverter.composeapp.generated.resources.error_timeout
import currencyconverter.composeapp.generated.resources.error_too_many_requests
import currencyconverter.composeapp.generated.resources.error_unknown
import org.jetbrains.compose.resources.stringResource

@Composable
fun mapErrorToMessage(error: DataFetchAppError): String {
    return when (error) {
        is DataFetchAppError.Remote -> when (error) {
            DataFetchAppError.Remote.NO_INTERNET -> stringResource(Res.string.error_no_internet)
            DataFetchAppError.Remote.REQUEST_TIMEOUT -> stringResource(Res.string.error_timeout)
            DataFetchAppError.Remote.SERVER -> stringResource(Res.string.error_server)
            DataFetchAppError.Remote.SERIALIZATION -> stringResource(Res.string.error_serialization)
            DataFetchAppError.Remote.TOO_MANY_REQUESTS -> stringResource(Res.string.error_too_many_requests)
            DataFetchAppError.Remote.UNKNOWN -> stringResource(Res.string.error_unknown)
        }

        is DataFetchAppError.Local -> when (error) {
            DataFetchAppError.Local.DISK_FULL -> stringResource(Res.string.error_disk_full)
            DataFetchAppError.Local.UNKNOWN -> stringResource(Res.string.error_local_unknown)
        }
    }
}
