package com.example.new_kuz.presentation.screens.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.new_kuz.tertiaryContainerLightMediumContrast

@Composable
fun isActive(active: Boolean) {
    Box(modifier = Modifier
        .size(60.dp, 30.dp)
        .padding(1.dp)
        .border(
            width = Dp.Hairline,
            color = if (active) tertiaryContainerLightMediumContrast else MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(50)
        )){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier
                .background(
                    if (active) tertiaryContainerLightMediumContrast else MaterialTheme.colorScheme.primary,
                    CircleShape
                )
                .size(4.dp)
                .clip(CircleShape),)
            Text(
                text = if (active) "Pending" else "Active",
                color = if (active) tertiaryContainerLightMediumContrast else MaterialTheme.colorScheme.primary,
                style = LocalTextStyle.current.copy(
                    fontSize = 9.sp
                )
            )
        }

    }
}

@Composable
fun isConnected(connected: Boolean) {
    Box(modifier = Modifier
        .size(50.dp, 30.dp)
        .padding(1.dp)
        .background(
            color = if (!connected) tertiaryContainerLightMediumContrast else Color.Green,
            shape = RoundedCornerShape(50)
        )){
        Text(
            text = if (!connected) "Pending" else "Active",
            color = if (!connected) Color.Black else Color.White,
            modifier = Modifier.align(
                Alignment.Center
            ),
            style = LocalTextStyle.current.copy(
                fontSize = 9.sp
            )
        )
    }
}