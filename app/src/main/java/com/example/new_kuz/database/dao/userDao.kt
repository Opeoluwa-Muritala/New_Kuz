package com.example.new_kuz.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.new_kuz.domain.modules.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: Users)

    @Query("SELECT * FROM USERS WHERE uid = :uid")
    fun getUserData(uid: String) : Users

    @Query("SELECT * FROM USERS")
    fun getAllUsers() : List<Users>

}