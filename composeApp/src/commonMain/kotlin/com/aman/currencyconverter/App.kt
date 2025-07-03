package com.aman.currencyconverter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.aman.core.data.HttpClientFactory
import com.aman.currencyconverter.data.remote.KtorRemoteExchangeRateDataSource
import com.aman.currencyconverter.data.repository.DefaultCurrencyExchangeRepository
import com.aman.currencyconverter.presentation.ui.CurrencyConverterScreenRoot
import com.aman.currencyconverter.presentation.viewmodel.CurrencyViewModel
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    MaterialTheme {
        CurrencyConverterScreenRoot(
            viewModel = remember { CurrencyViewModel(
                repository = DefaultCurrencyExchangeRepository(
                    remoteExchangeRateDataSource = KtorRemoteExchangeRateDataSource(
                        httpClient = HttpClientFactory.create(engine)
                    )
                )
            )
            }
        )
    }
}
