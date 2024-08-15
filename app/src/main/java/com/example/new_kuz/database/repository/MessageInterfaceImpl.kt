package com.example.new_kuz.database.repository

import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.repository.MessageInterface
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject


class MessageInterfaceImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore
): MessageInterface {
    override fun allMessages(): List<Messages> {
        var list: List<Messages> = mutableListOf<Messages>()
        db.collection("Messages").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null){
                list = value.toObjects()
            }
        }
        return list
    }

    override fun myMessages(uid: String): List<Messages> {
        return allMessages().filter { it.sentby == uid || it.sentto == uid}
    }

    override fun getChat(senderUid: String, recieverUid: String): List<Messages> {
        return myMessages(senderUid).filter { it.sentby == senderUid && it.sentto == recieverUid }
    }

    override fun addNewMessage(messages: Messages) {
        db.collection("Messages").document().set(messages)
    }

}