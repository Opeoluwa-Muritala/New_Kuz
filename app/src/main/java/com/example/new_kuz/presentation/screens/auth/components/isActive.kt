package com.example.new_kuz.presentation.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun isActive(active: Boolean) {
    Box(modifier = Modifier.size(80.dp, 30.dp)
        .padding(1.dp)
        .background(
            color = if (!active) Color.Yellow else Color.Green,
            shape = RoundedCornerShape(50)
        )){
        Text(
            text = if (!active) "Pending" else "Active",
            color = if (!active) Color.Black else Color.White,
            modifier = Modifier.align(
                Alignment.Center
            ),
            style = LocalTextStyle.current.copy(
                fontSize = 18.sp
            )
        )
    }
}