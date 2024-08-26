package com.example.new_kuz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.database.dao.UserDao
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users

@TypeConverters(StringListConverter::class)
@Database(
    entities = [Messages::class, Users::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase :RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun messagesDao(): MessageDao
}