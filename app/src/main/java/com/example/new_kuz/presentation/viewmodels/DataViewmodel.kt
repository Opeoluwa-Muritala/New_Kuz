package com.example.new_kuz.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.database.dao.UserDao
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataViewmodel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
    private val messageDao: MessageDao
): ViewModel() {
    val currentUser = auth.currentUser


    init {
        getAllUserDetails()
        getMessages()
    }

    private fun getAllUserDetails() {
        val users = mutableListOf<Users>()
        db.collection("Users").document("${currentUser?.uid}").get()
            .addOnSuccessListener {documentSnapshots ->

                val userDetails = documentSnapshots.toObject(Users::class.java)
                viewModelScope.launch {
                    userDao.saveUser(userDetails!!)
                }

                db.collection("Users").addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        for (document in value) {
                            if (document.toObject(Users::class.java).uid != currentUser?.uid){
                                users.add(document.toObject(Users::class.java))
                                viewModelScope.launch {
                                    userDao.saveUser(document.toObject(Users::class.java))
                                }
                            }
                        }
                    }
                }
            }
    }
    private fun getMessages(){
        val messages = mutableListOf<Messages>()

        db.collection("Messages").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                for (document in value) {
                    messages.add(document.toObject(Messages::class.java))
                    viewModelScope.launch {
                        messageDao.saveMessage(document.toObject(Messages::class.java))
                    }
                }
                messages.clear()
            }
        }
    }
}