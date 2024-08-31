package com.example.new_kuz.presentation.events

import com.example.new_kuz.domain.modules.Users

sealed class ChatScreenEvent {
    data class onQueryChange(val query: String): ChatScreenEvent()
    data class onChatClick(val user: Users): ChatScreenEvent()
    data object onArchiveClick: ChatScreenEvent()
}