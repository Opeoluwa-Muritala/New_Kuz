package com.example.new_kuz.domain.repository

import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun allMessages(): Flow<RequestState<List<Messages>>>
    fun myMessages(uid:String): Flow<RequestState<List<Messages>>>
    fun getChat(senderUid: String, recieverUid: String): Flow<RequestState<List<Messages>>>
    fun addNewMessage(messages: Messages) : Flow<RequestState<String>>
}