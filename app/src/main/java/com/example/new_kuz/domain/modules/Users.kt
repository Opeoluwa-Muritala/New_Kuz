package com.example.new_kuz.domain.modules

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Users(
    @PrimaryKey
    val uid: String = "",
    val name: String = "",
    val imageUrl: String? = null,
    val connectedUsers: List<String> = emptyList(),
    val archivedUsers: List<String> = emptyList(),
    val blockedUsers: List<String> = emptyList(),
    val active: Boolean = true,
    val dateOfBirth: String = "",
    val bio: String? = null,
    val gender: String = ""
)