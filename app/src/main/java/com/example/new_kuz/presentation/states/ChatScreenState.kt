package com.example.new_kuz.presentation.states

import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users

data class ChatScreenState(
    val chats: List<Users> = emptyList(),
    val connected: List<String> = emptyList(),
    val query: String = "",
    val currentUser: Users = Users(),
    val messages: List<Messages> = emptyList()
)