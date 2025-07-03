package com.aman.currencyconverter.presentation.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aman.currencyconverter.presentation.model.CurrencyConverterState
import com.aman.currencyconverter.presentation.ui.components.CurrencyDropdown
import com.aman.currencyconverter.presentation.viewmodel.CurrencyViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun CurrencyConverterScreenRoot(
    viewModel: CurrencyViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CurrencyConverterScreen(
        state = state
    )
}

@Composable
private fun CurrencyConverterScreen(
    state: CurrencyConverterState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(vertical = 80.dp, horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Currency Converter",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            CurrencyRow(
                currency = state.fromCurrency,
                amount = state.amount,
                isEditable = true,
                onCurrencyChange = { },
                onAmountChange = { }
            )

            Icon(
                imageVector = Icons.Filled.SwapVert,
                contentDescription = "Swap currencies",
                modifier = Modifier
                    .size(42.dp)
                    .clickable { }
                    .align(Alignment.Start)
            )

            CurrencyRow(
                currency = state.toCurrency,
                amount = state.convertedAmount ?: "",
                isEditable = true,
                onCurrencyChange = { },
                onAmountChange = { }
            )
        }
    }
}


@Composable
fun CurrencyRow(
    currency: String,
    amount: String = "0.0",
    isEditable: Boolean,
    onCurrencyChange: (String) -> Unit,
    onAmountChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyDropdown(
            label = "USD",
            selectedCurrency = currency,
            currencies = listOf("USD", "EUR", "INR", "JPY"),
            onCurrencySelected = onCurrencyChange
        )

        Spacer(modifier = Modifier.width(16.dp))

        if (isEditable) {
            TextField(
                value = amount,
                onValueChange = onAmountChange,
                modifier = Modifier.width(150.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 32.sp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray,
                    errorIndicatorColor = Color.Red,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
            )
        } else {
            Text(
                text = amount,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}
