package com.example.new_kuz.presentation.screens.appscreens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    modifier: Modifier = Modifier,
    state: DatePickerState,
    isOpen: Boolean,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "CANCEL",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        DatePickerDialog(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onConfirmButtonClick) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onDismissRequest) {
                    Text(text = dismissButtonText)
                }

            }
        ) {
            DatePicker(
                state = state,
                showModeToggle = true
            )
        }
    }
}