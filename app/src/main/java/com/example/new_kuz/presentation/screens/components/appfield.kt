package com.example.new_kuz.presentation.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun appField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    error: Boolean = false,
    placeholderText: String = "",
    transform: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    leadingIcon: @Composable() (() -> Unit)? = null,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    trailingIcon: @Composable() (() -> Unit)? = null,
    supportingText: String? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            errorContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            errorIndicatorColor = Color.Red,
            focusedLabelColor = Color.Black,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            disabledTextColor =  Color.Black,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary
        ),
        label = {
            Text(text = label, color = MaterialTheme.colorScheme.onSecondary)
        },
        isError = error,
        placeholder = {
            Text(text = placeholderText, color = MaterialTheme.colorScheme.onSecondary)
        },
        visualTransformation = transform,
        readOnly = readOnly,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = leadingIcon,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon ,
        supportingText = { Text(text = supportingText.orEmpty())},
    )
}