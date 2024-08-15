package com.example.new_kuz.util

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarDuration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.chrono.ChronoPeriod
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle
import java.util.Calendar.HOUR_OF_DAY

fun Long?.changeMillisToDateString(): String{
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"))
}

fun Long?.changeMillisToTimeString(): String{
    val date: LocalDateTime = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    } ?: LocalDateTime.now()
    return date.format(DateTimeFormatter.ofPattern("HH:mm a"))
}

sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ): SnackBarEvent()
}