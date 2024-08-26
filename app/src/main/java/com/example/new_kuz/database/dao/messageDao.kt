package com.example.new_kuz.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Upsert
    suspend fun saveMessage(messages: Messages)

    @Query("SELECT * FROM Messages WHERE sentby = :senderuid AND sentto = :recieveruid")
    fun getMessage(senderuid: String , recieveruid: String) : Flow<List<Messages>>

    @Query("SELECT * FROM Messages WHERE sentby = :senderuid OR sentto = :senderuid")
    fun getAllMessage(senderuid: String) : Flow<List<Messages>>
}