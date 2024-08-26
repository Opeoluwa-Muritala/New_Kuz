package com.example.new_kuz.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.database.dao.UserDao
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserRepository
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
): ViewModel() {
    val currentUser = auth.currentUser

    private val _requestState = MutableSharedFlow<RequestState<String>>()
    val requestState = _requestState.asSharedFlow()

    private val _state = MutableStateFlow(ChatScreenState())
    val state = _state.asStateFlow()


    init {
        getAllUserDetails()

        getMessages()
    }

    private fun getAllUserDetails() {

        viewModelScope.launch {
            userRepository.getUser("${currentUser?.uid}")
            userRepository.getAllUsers().collectLatest {request->
                if (request.isSuccess()){
                    _state.update {
                        it.copy(
                            chats = request.getSuccessDataOrNull() ?: emptyList()
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

    private fun getMessages() {

    }
}