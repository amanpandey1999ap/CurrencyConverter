package com.aman.currencyconverter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.aman.currencyconverter.currencyConverterFeature.presentation.ui.CurrencyConverterScreenRoot
import com.aman.currencyconverter.currencyConverterFeature.presentation.viewmodel.CurrencyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<CurrencyViewModel>()

    MaterialTheme {
        CurrencyConverterScreenRoot(
            viewModel = viewModel
        )
    }
}
