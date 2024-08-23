package com.example.new_kuz.domain.modules

import androidx.room.Entity

@Entity
data class Messages(
    val sentby: String = "",
    val sentto: String = "",
    val message: String = "",
    val date: String = "",
    val timeline: String = "",
    val images: List<String> = emptyList(),
)
