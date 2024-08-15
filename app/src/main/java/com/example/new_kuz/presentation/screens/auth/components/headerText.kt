package com.example.new_kuz.presentation.screens.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun headerDetailText(
    text: String,
    details:String?,
    headerColor:Color = MaterialTheme.colorScheme.onSecondary,
    detailTextColor:Color = headerColor,
    size: Int = 30
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = size.sp,
            fontWeight = FontWeight.Bold,
            color = headerColor
        )
        Spacer(modifier = Modifier.height(20.dp))
        details?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = detailTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}