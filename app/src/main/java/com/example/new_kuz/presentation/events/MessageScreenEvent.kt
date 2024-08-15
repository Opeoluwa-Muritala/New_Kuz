package com.example.new_kuz.presentation.events

import android.net.Uri
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.navigation.User

sealed class MessageScreenEvent {
    data class SelectPhoto(val photo: List<Uri>?) : MessageScreenEvent()
    data class onConfirmSheetEvent(val event: String): MessageScreenEvent()
    data class onSearchQueryChange(val query: String): MessageScreenEvent()
    data class onContactClick(val user: Users): MessageScreenEvent()
    data class onTaskChange(val task: String): MessageScreenEvent()
    data class onMessageStateChange(val message:String): MessageScreenEvent()
    data object sendMessage: MessageScreenEvent()
    data object onBottomSheetStateChange: MessageScreenEvent()
    data object onMenuStateChange: MessageScreenEvent()
    data object onBackClick: MessageScreenEvent()
    data object onShowSearchBarChange: MessageScreenEvent()
}