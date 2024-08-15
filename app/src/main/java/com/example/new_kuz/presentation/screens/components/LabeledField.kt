package com.example.new_kuz.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.new_kuz.KUZTheme

@Composable
fun LabelledTextField(
    modifier: Modifier = Modifier,
    title: String = "Name",
    fieldText: String,
    onFieldTextChange: (String) -> Unit
) {
    KUZTheme {
        Column {
            Text(title, color = MaterialTheme.colorScheme.onSecondary)
            appField(
                modifier = modifier.padding(start = 5.dp),
                value = fieldText,
                onValueChange = onFieldTextChange,
                placeholderText = "Enter $title"
            )
        }
    }

}