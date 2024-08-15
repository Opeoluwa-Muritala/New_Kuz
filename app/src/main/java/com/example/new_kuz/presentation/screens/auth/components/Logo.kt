package com.example.new_kuz.presentation.screens.auth.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.new_kuz.R

@Composable
fun Logo(
    color: Color,
    fontSize: Int
) {
    Icon(
        painter = painterResource(id = R.drawable.kuz_white),
        modifier = Modifier.size(fontSize.dp),
        tint = color,
        contentDescription = "KUZ",
    )
}