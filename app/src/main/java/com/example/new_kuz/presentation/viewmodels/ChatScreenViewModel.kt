package com.example.new_kuz.presentation.viewmodels


import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.MessageRepository
import com.example.new_kuz.domain.repository.UserRepository
import com.example.new_kuz.presentation.events.ChatScreenEvent
import com.example.new_kuz.presentation.states.ChatScreenState
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository,
    private val messageRepository: MessageRepository
): ViewModel() {
    val currentUser = auth.currentUser

    private val _state = MutableStateFlow(ChatScreenState())
    val state = _state.asStateFlow()


    init {
        getAllUserDetails()
        getMessages()
    }

    private fun getAllUserDetails() {

        viewModelScope.launch {
            val allUsers = userRepository.getAllUsers()
            userRepository.getUser("${currentUser?.uid}").collectLatest {request->
                _state.update {
                    it.copy(
                        currentUser = request.getSuccessDataOrNull() ?: Users(),
                        connected = request.getSuccessDataOrNull()?.connectedUsers ?: emptyList(),
                        archived =  request.getSuccessDataOrNull()?.archivedUsers ?: emptyList()
                    )
                }
            }
            allUsers.collectLatest { request ->
                if (request.isSuccess()) {
                    _state.update {
                        it.copy(
                            chats = request.getSuccessDataOrNull()?.filter {
                                !state.value.archived.contains(it.uid)
                            }?.filter {!state.value.currentUser.blockedUsers.contains(it.uid)  } ?: emptyList(),
                        )
                    }
                } else if (request.isLoading()) {
                    showSnackbar("Loading...", duration = SnackbarDuration.Short)
                } else {
                    showSnackbar(request.getErrorData())
                }
            }
        }


    }

    private fun getMessages() {
        viewModelScope.launch {
            val messages = messageRepository.myMessages("${currentUser?.uid}")
            messages.collectLatest {request ->
                if (request.isSuccess()) {
                    _state.update {
                        it.copy(
                            messages = request.getSuccessDataOrNull() ?: emptyList()
                        )
                    }
                } else {
                    showSnackbar(request.getErrorData())
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
            is ChatScreenEvent.onArchiveClick -> {}
        }
    }


}