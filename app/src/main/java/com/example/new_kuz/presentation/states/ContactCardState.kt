package com.example.new_kuz.presentation.states

import com.example.new_kuz.domain.modules.Users

data class ContactCardState(
    val name: String = "",
    val lastSeen: String = "...",
    val gender: String = "e.g: Male",
    val bio: String = "",
    val currentUser: Users = Users(),
    val otherUsers: Users = Users()
)