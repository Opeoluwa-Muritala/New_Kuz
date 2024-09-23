package com.example.new_kuz.presentation.viewmodels

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.database.dao.UserDao
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


@HiltViewModel
class DataViewmodel @Inject constructor(
    private val userDao: UserDao,
    private val messageDao: MessageDao,
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {
    private val _loadingState = MutableStateFlow<Boolean>(true)
    val loadingState = _loadingState
        .onStart { fetchData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            true
        )
    var users = mutableListOf<Users>()
    val messages = mutableListOf<Messages>()

    private fun fetchData() {
        val temp = mutableListOf<Messages>()
        viewModelScope.launch{
                try {
                    _loadingState.update { true }
                    getAllUserDetails()
                    users.forEach {
                        withContext(Dispatchers.IO) {
                            userDao.saveUser(it)
                        }
                    }
                    delay(3000)
                    viewModelScope.launch {
                        getMessages()
                        messages.forEach {
                            if (!temp.contains(it)) {
                                temp.add(it)
                            }
                        }
                            temp.forEach {
                            withContext(Dispatchers.IO) {
                                messageDao.saveMessage(it)
                            }
                        }
                        _loadingState.update { false }
                        Log.d("Messages1", messages.toString())
                    }
                    delay(3000)
                } catch (e: Exception) {
                    coroutineContext.ensureActive()
                    Log.e("FetchData", e.localizedMessage!!.toString())
                delay(3000)
            }
        }
    }


    private fun getAllUserDetails() {
        val currentUser = auth.currentUser
        db.collection("Users").document("${currentUser?.uid}").get()
            .addOnSuccessListener { documentSnapshots ->
                users.add(documentSnapshots.toObject(Users::class.java)!!)
                db.collection("Users").addSnapshotListener { value, error ->
                    if (error != null) {
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        for (document in value) {
                            if (document.toObject(Users::class.java).uid != currentUser?.uid) {
                                users.add(document.toObject(Users::class.java))
                            }
                        }
                    }
                }
            }

    }

    private fun getMessages() {

        db.collection("Messages").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                for (document in value) {
                    messages.add(document.toObject(Messages::class.java))
                }
            }
        }
        _loadingState.update {
            true
        }
    }
}

suspend fun showSnackbar(text: String, action: SnackbarAction? = null, duration: SnackbarDuration? = null) {
    SnackbarController.sendEvent(
        event = SnackbarEvent(
            message = text,
            action = action,
            duration = duration
        )
    )
}