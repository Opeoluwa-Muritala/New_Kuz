package com.example.new_kuz.domain.repository

import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(uid: String): Flow<RequestState<Users>>
    fun getAllUsers(): Flow<RequestState<List<Users>>>
    fun getArchived(users: List<String>): Flow<RequestState<List<Users>>>
    fun getBlocked(users: List<String>): Flow<RequestState<List<Users>>>
    fun getConnected(users: List<String>):Flow<RequestState<List<Users>>>
    fun getNotConnected(users: List<String>):Flow<RequestState<List<Users>>>
    fun updateUser(user: Users): Flow<RequestState<String>>
}