package com.aman.currencyconverter.currencyConverterFeature.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aman.currencyconverter.currencyConverterFeature.presentation.model.CurrencyConverterAction
import com.aman.currencyconverter.currencyConverterFeature.presentation.model.CurrencyConverterState
import com.aman.currencyconverter.currencyConverterFeature.presentation.ui.components.CurrencyDropdown
import com.aman.currencyconverter.currencyConverterFeature.presentation.viewmodel.CurrencyViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CurrencyConverterScreenRoot(
    viewModel: CurrencyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CurrencyConverterScreen(
        state = state,
        onFromCurrencyChange = {
            viewModel.handleAction(
                CurrencyConverterAction.FromCurrencyChanged(
                    it
                )
            )
        },
        onToCurrencyChange = { viewModel.handleAction(CurrencyConverterAction.ToCurrencyChanged(it)) },
        onAmountChange = { viewModel.handleAction(CurrencyConverterAction.AmountChanged(it)) },
        onSwap = { viewModel.handleAction(CurrencyConverterAction.SwapCurrencies) },
    )
}

@Composable
fun CurrencyConverterScreen(
    state: CurrencyConverterState,
    onFromCurrencyChange: (String) -> Unit,
    onToCurrencyChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
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
            amount = state.amount,
            onCurrencyChange = onFromCurrencyChange,
            onAmountChange = onAmountChange
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
            amount = state.convertedAmount ?: "0.0",
            onCurrencyChange = onToCurrencyChange,
            onAmountChange = {}
        )
    }
}

@Composable
fun CurrencyRow(
    currency: String,
    amount: String,
    onCurrencyChange: (String) -> Unit,
    onAmountChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyDropdown(
            selectedCurrency = currency,
            currencies = listOf("USD", "EUR", "INR", "JPY"),
            onCurrencySelected = onCurrencyChange
        )

        Spacer(modifier = Modifier.width(16.dp))

        BasicTextField(
            value = amount,
            onValueChange = onAmountChange,
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 36.sp
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .width(150.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        )
    }
}
