package com.aman.currencyconverter.currencyConverterFeature.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyRow(
    currency: String,
    amount: String,
    currencies: List<String>,
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
            currencies = currencies,
            onCurrencySelected = onCurrencyChange
        )

        Spacer(modifier = Modifier.width(16.dp))

        BasicTextField(
            value = amount,
            onValueChange = { if (it.length <= 10) onAmountChange(it) },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 32.sp,
                lineHeight = 36.sp
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier
                .width(170.dp)
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
