package com.example.new_kuz.presentation.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserRepository
import com.example.new_kuz.presentation.events.ContactCardEvent
import com.example.new_kuz.presentation.events.HomeScreenEvent
import com.example.new_kuz.presentation.states.ContactCardState
import com.example.new_kuz.presentation.states.HomeScreenState
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
class ContactCardViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(ContactCardState())
    val state = _state.asStateFlow()

    private val _requestState = MutableSharedFlow<RequestState<String>>()
    val requestState = _requestState.asSharedFlow()

    val currentUser = auth.currentUser


    init {
        getAllUserDetails()
    }


    private fun getAllUserDetails() {
        viewModelScope.launch {
            val allUsers = userRepository.getAllUsers()
            userRepository.getUser("${currentUser?.uid}").collectLatest { request ->
                val userDetails = request.getSuccessDataOrNull()
                _state.update {
                    it.copy(
                        currentUser = userDetails!!
                    )
                }
            }
        }
    }
        fun onEvents(event: ContactCardEvent) {
            when (event) {
                ContactCardEvent.archiveContact -> TODO()
                ContactCardEvent.blockContact -> TODO()
                ContactCardEvent.onBackClick -> TODO()
            }
        }

}