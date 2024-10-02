package com.example.new_kuz.database.repository

import android.net.Uri
import androidx.core.net.toUri
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
            val user: Users
            try {
                withContext(Dispatchers.IO) {
                     user = userDao.getUserData(uid)
                }
                emit(RequestState.Success(user))
            } catch (e:Exception){
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }

        }
    }

    override fun getAllUsers(): Flow<RequestState<List<Users>>> {
        return flow {
            val users: List<Users>
            try {
                withContext(Dispatchers.IO){
                     users = userDao.getAllUsers()
                }
                emit(RequestState.Success(users))
            } catch (e: Exception) {
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }

        }
    }

    override fun getArchived(users: List<String>): Flow<RequestState<List<Users>>> {
        return flow {  }
    }

    override fun getBlocked(users: List<String>): Flow<RequestState<List<Users>>> {
        return flow {  }
    }

    override fun getConnected(users: List<String>): Flow<RequestState<List<Users>>> {
        return flow {  }
    }

    override fun getNotConnected(users: List<String>): Flow<RequestState<List<Users>>> {
        return flow {  }
    }

    override fun updateUser(user: Users): Flow<RequestState<String>> {
        return flow {
            val url = uploadImage(user.imageUrl?.toUri())
            db.collection("Users").document("${auth.currentUser?.uid}")
                .update(
                    mapOf(
                        "imageUrl" to url,
                        "name" to user.name,
                        "bio" to user.bio,
                        "gender" to user.gender,
                        "dateOfBirth" to user.dateOfBirth
                    )
                )
            userDao.saveUser(user)
        }
    }
    private fun uploadImage(imageUri: Uri?): Uri? {
        var storageRef = storage.reference.child("Images")
        var downloadUrl: Uri? = null

        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        downloadUrl = uri
                    }
                }
            }
        }
        return downloadUrl
    }
}