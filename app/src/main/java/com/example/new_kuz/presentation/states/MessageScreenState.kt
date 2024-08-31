package com.example.new_kuz.presentation.states

import android.net.Uri
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users

data class MessageScreenState(
    val showBottomSheet: Boolean = false,
    val bottomSheetTask: String = "",
    val showSearchBar: Boolean = false,
    val searchQuery: String = "",
    val expandMenuBar: Boolean = false,
    val message: String = "",
    val sendPhoto: List<Uri> = emptyList(),
    val currentUser: String = "",
    val receiver: Users? = null,
    val images: List<String> = emptyList(),
    val messages: Map<String, List<Messages>>,
    val dates: List<String> = emptyList()
)