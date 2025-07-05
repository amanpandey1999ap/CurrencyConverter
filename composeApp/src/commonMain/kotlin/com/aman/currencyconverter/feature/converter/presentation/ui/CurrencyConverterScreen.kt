package com.aman.currencyconverter.feature.converter.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aman.currencyconverter.core.utils.showMessage
import com.aman.currencyconverter.feature.converter.data.mappers.mapErrorToMessage
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.feature.converter.presentation.model.CurrencyConverterState
import com.aman.currencyconverter.feature.converter.presentation.ui.components.CurrencyRow
import com.aman.currencyconverter.feature.converter.presentation.viewmodel.CurrencyViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CurrencyConverterScreenRoot(
    viewModel: CurrencyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val errorMessageText = state.errorMessage?.let { mapErrorToMessage(it) }

    LaunchedEffect(errorMessageText) {
        if (errorMessageText != null) {
            showMessage(errorMessageText)
            viewModel.handleAction(CurrencyConverterAction.ErrorDismissed)
        }
    }

    CurrencyConverterScreen(
        state = state,
        onFromCurrencyChange = { viewModel.handleAction(CurrencyConverterAction.FromCurrencyChanged(it)) },
        onToCurrencyChange = { viewModel.handleAction(CurrencyConverterAction.ToCurrencyChanged(it)) },
        onFromAmountChange = { viewModel.handleAction(CurrencyConverterAction.FromAmountChanged(it)) },
        onToAmountChange = { viewModel.handleAction(CurrencyConverterAction.ToAmountChanged(it)) },
        onSwap = { viewModel.handleAction(CurrencyConverterAction.SwapCurrencies) }
    )
}

@Composable
private fun CurrencyConverterScreen(
    state: CurrencyConverterState,
    onFromCurrencyChange: (String) -> Unit,
    onToCurrencyChange: (String) -> Unit,
    onFromAmountChange: (String) -> Unit,
    onToAmountChange: (String) -> Unit,
    onSwap: () -> Unit
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF20002C),
            Color(0xFF6F1E51),
            Color(0xFFC44569)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBackground)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Currency Converter",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
            modifier = Modifier.padding(top = 64.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        CurrencyRow(
            currency = state.fromCurrency,
            amount = state.fromAmount,
            currencies = state.currencies,
            onCurrencyChange = onFromCurrencyChange,
            onAmountChange = onFromAmountChange,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            imageVector = Icons.Filled.SwapVert,
            contentDescription = "Swap currencies",
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .clickable { onSwap() }
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(32.dp))

        CurrencyRow(
            currency = state.toCurrency,
            amount = state.toAmount,
            currencies = state.currencies,
            onCurrencyChange = onToCurrencyChange,
            onAmountChange = onToAmountChange
        )
    }
}
