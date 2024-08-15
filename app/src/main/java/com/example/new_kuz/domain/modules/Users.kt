package com.example.new_kuz.domain.modules

import kotlinx.serialization.Serializable

@Serializable
data class Users(
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