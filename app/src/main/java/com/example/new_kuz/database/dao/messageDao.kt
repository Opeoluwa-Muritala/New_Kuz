package com.example.new_kuz.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.new_kuz.domain.modules.Messages

@Dao
interface MessageDao {
    @Upsert
    suspend fun saveMessage(messages: Messages)

    @Query("SELECT * FROM Messages WHERE sentby = :senderuid AND sentto = :recieveruid")
    fun getMessages(senderuid: String , recieveruid: String) : List<Messages>

    @Query("SELECT * FROM Messages WHERE sentby = :senderuid OR sentto = :senderuid")
    fun getAllSenderMessage(senderuid: String) : List<Messages>

    @Query("SELECT * FROM Messages")
    fun getAllMessage() : List<Messages>
}