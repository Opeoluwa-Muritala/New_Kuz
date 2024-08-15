package com.example.new_kuz.presentation.events

import com.example.new_kuz.domain.modules.Users

sealed class HomeScreenEvent {
    data class onAvailableChange(val available: Boolean): HomeScreenEvent()
    data class onQueryChange(val query: String): HomeScreenEvent()
    data class onChatClick(val contact: Users): HomeScreenEvent()
    data class onConnectClick(val contact: Users): HomeScreenEvent()
}
