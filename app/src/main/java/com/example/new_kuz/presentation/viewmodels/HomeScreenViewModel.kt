package com.example.new_kuz.presentation.viewmodels

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserRepository
import com.example.new_kuz.presentation.events.HomeScreenEvent
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
import okhttp3.HttpUrl.Companion.toHttpUrl
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val userRepository: UserRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
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
                        userName = userDetails?.name.orEmpty(),
                        available = userDetails?.active ?: false,
                        users = userDetails ?: Users(),
                        connected = userDetails?.connectedUsers.orEmpty()
                    )
                }
            }
            allUsers.collectLatest { request ->
                if (request.isSuccess()) {
                    val users = request.getSuccessDataOrNull().orEmpty()
                    _state.update {
                        it.copy(
                            contacts = users.filter { user ->
                                state.value.users.uid != user.uid
                            },
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


    fun onEvents(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.onAvailableChange -> {
                _state.update {
                    it.copy(
                        available = !state.value.available
                    )
                }
                updateAvailableState(!state.value.available)
            }
            is HomeScreenEvent.onChatClick -> {}
            is HomeScreenEvent.onConnectClick -> {
                connectUsers(event.contact)
            }
            is HomeScreenEvent.onQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.query,
                        contacts = state.value.contacts.filter {user ->
                            user.name.lowercase().contains(event.query.lowercase())
                        }
                    )
                }
            }
        }
    }

    private fun updateAvailableState(available: Boolean) {
        db.collection("Users").document("${currentUser?.uid}")
            .update(mapOf("available" to available))
    }

    private fun connectUsers(user: Users) {
        val tmpData = mutableListOf<String>()
        db.collection("Users").document("${currentUser?.uid}").get()
            .addOnSuccessListener {
                _requestState.tryEmit(RequestState.Loading)
                val oldUserData = it.toObject(Users::class.java)

                tmpData.addAll(oldUserData?.connectedUsers!!)
                if (!tmpData.contains(user.uid)) {
                    tmpData.add(user.uid)
                }
                db.collection("Users").document("${currentUser?.uid}").update(
                    mapOf("connectedUsers" to tmpData.toList())
                ) .addOnSuccessListener {
                    _state.update {
                        it.copy(
                            connected = tmpData
                        )
                    }
                    _requestState.tryEmit(RequestState.Success("User has been sent a connect request"))
                } .addOnFailureListener {
                    _requestState.tryEmit(RequestState.Error("Failed to connect user"))
                }
            }
    }
}