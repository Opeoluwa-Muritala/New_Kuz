package com.example.new_kuz.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.presentation.events.ChatScreenEvent
import com.example.new_kuz.presentation.states.ChatScreenState
import com.example.new_kuz.presentation.states.HomeScreenState
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
): ViewModel() {
    val currentUser = auth.currentUser

    private val _requestState = MutableSharedFlow<RequestState<String>>()
    val requestState = _requestState.asSharedFlow()

    private val _state = MutableStateFlow(ChatScreenState())
    val state = _state.asStateFlow()


    init {
        getAllUserDetails()
//        getCurrentUserDetails()
//        getAllContacts()
        getMessages()
    }

    private fun getAllUserDetails() {
        val users = mutableListOf<Users>()
        db.collection("Users").document("${currentUser?.uid}").get()
            .addOnSuccessListener {documentSnapshots ->
                val userDetails = documentSnapshots.toObject(Users::class.java)
                _state.update {
                    it.copy(
                        currentUser = userDetails!!,
                        connected = userDetails.connectedUsers
                    )
                }
                db.collection("Users").addSnapshotListener { value, error ->
                    if (error != null) {
                        viewModelScope.launch {
                            _requestState.emit(RequestState.Error(error.localizedMessage.orEmpty()))
                        }
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        for (document in value) {
                            if (document.toObject(Users::class.java).uid != currentUser?.uid){
                                users.add(document.toObject(Users::class.java))
                            }
                        }
                        _state.update {
                            it.copy(
                                chats = users.toList().filter {user ->
                                    userDetails?.connectedUsers!!.contains(user.uid)
                                }
                            )
                        }
                    }
                }
            }
    }

    fun onEvent(event: ChatScreenEvent){
        when (event) {
            is ChatScreenEvent.onQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.query,
                        chats = state.value.chats.filter {user ->
                            user.name.lowercase().contains(event.query.lowercase())
                        }
                    )
                }
            }

            is ChatScreenEvent.onChatClick -> {}
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

                }
                _state.update {
                    it.copy(
                        messages = messages.toList().filter {message ->
                            message.sentto == currentUser?.uid || message.sentby == currentUser?.uid
                        }
                    )
                }
                messages.clear()
            }
        }
    }
}