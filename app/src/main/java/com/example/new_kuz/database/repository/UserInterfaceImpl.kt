package com.example.new_kuz.database.repository

import com.example.new_kuz.database.dao.UserDao
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserRepository
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth,
    private val userDao: UserDao
): UserRepository {


    override fun getUser(uid: String): Flow<RequestState<Users>> {
        return flow {
            emit(RequestState.Loading)
            try {
                val user = userDao.getUserData(uid)
                emit(RequestState.Success(user))
            } catch (e:Exception){
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }

        }
    }

    override fun getAllUsers(): Flow<RequestState<List<Users>>> {
        return flow {
            emit(RequestState.Loading)

            try {
                val users = userDao.getAllUsers()
                emit(RequestState.Success(users))
            } catch (e: Exception) {
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }

        }
    }

    override fun getArchived(users: List<String>): Flow<RequestState<List<Users>>> {
        TODO("Not yet implemented")
    }

    override fun getBlocked(users: List<String>): Flow<RequestState<List<Users>>> {
        TODO("Not yet implemented")
    }

    override fun getConnected(users: List<String>): Flow<RequestState<List<Users>>> {
        TODO("Not yet implemented")
    }

    override fun getNotConnected(users: List<String>): Flow<RequestState<List<Users>>> {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: Users): Flow<RequestState<String>> {
        TODO("Not yet implemented")
    }
}