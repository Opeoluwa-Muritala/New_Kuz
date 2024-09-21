package com.example.new_kuz.database.repository

import android.net.Uri
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.repository.MessageRepository
import com.example.new_kuz.util.RequestState
import com.example.new_kuz.util.changeMillisToDateString
import com.example.new_kuz.util.changeMillisToTimeString
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MessageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
    private val messageDao: MessageDao
): MessageRepository {

    override fun myMessages(uid: String): Flow<RequestState<List<Messages>>> {
        return flow {
            val messagges: List<Messages>
            try {
                withContext(Dispatchers.IO) {
                    messagges = messageDao.getAllSenderMessage(uid)
                }
                emit(RequestState.Success(messagges))
            } catch (e:Exception){
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }
        }
    }

    override fun getChat(
        senderUid: String,
        recieverUid: String
    ): Flow<RequestState<List<Messages>>> {
        return flow {
            val messagges: List<Messages>
            try {
                withContext(Dispatchers.IO) {
                    messagges = messageDao.getMessages(senderuid = senderUid, recieveruid = recieverUid)
                }
                emit(RequestState.Success(messagges))
            } catch (e:Exception){
                emit(RequestState.Error(e.localizedMessage?.toString() ?: ""))
            }
        }
    }

    override fun addNewMessage(messages: Messages): Flow<RequestState<String>> {
        return flow {
            db.collection("Messages").add(messages)
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