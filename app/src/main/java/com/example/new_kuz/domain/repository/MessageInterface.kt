package com.example.new_kuz.domain.repository

import com.example.new_kuz.domain.modules.Messages

interface MessageInterface {
    fun allMessages(): List<Messages>
    fun myMessages(uid:String): List<Messages>
    fun getChat(senderUid: String, recieverUid: String): List<Messages>
    fun addNewMessage(messages: Messages)
}