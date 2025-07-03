package com.aman.currencyconverter

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.aman.currencyconverter.presentation.ui.CurrencyConverterScreenRoot
import com.aman.currencyconverter.presentation.viewmodel.CurrencyViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        CurrencyConverterScreenRoot(
            viewModel = remember { CurrencyViewModel() }
        )
    }
}