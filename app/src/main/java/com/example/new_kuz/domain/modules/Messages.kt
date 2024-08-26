package com.example.new_kuz.domain.modules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Messages(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val sentby: String = "",
    val sentto: String = "",
    val message: String = "",
    val date: String = "",
    val timeline: String = "",
    val images: List<String> = emptyList(),
)
