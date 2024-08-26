package com.example.new_kuz.database.repository

import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.repository.MessageRepository
import com.example.new_kuz.util.RequestState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MessageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): MessageRepository {
    override fun allMessages(): Flow<RequestState<List<Messages>>> {
        TODO("Not yet implemented")
    }

    override fun myMessages(uid: String): Flow<RequestState<List<Messages>>> {
        TODO("Not yet implemented")
    }

    override fun getChat(
        senderUid: String,
        recieverUid: String
    ): Flow<RequestState<List<Messages>>> {
        TODO("Not yet implemented")
    }

    override fun addNewMessage(messages: Messages): Flow<RequestState<String>> {
        TODO("Not yet implemented")
    }
}