package com.example.new_kuz.presentation.viewmodels

import android.net.Uri
import androidx.compose.material3.SnackbarDuration
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserRepository
import com.example.new_kuz.presentation.events.SettingsEvent
import com.example.new_kuz.presentation.states.SettingsState
import com.example.new_kuz.util.RequestState
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.plcoding.globalsnackbarscompose.SnackbarAction
import com.plcoding.globalsnackbarscompose.SnackbarController
import com.plcoding.globalsnackbarscompose.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    val currentUser = auth.currentUser

    val userdata = userRepository.getUser("${currentUser?.uid}")

    init {
        getCurrentUserDetails()
    }

    private fun getCurrentUserDetails() {
        viewModelScope.launch {
            var users: Users?
            userdata.collect { userDetails ->
                if (userDetails.isSuccess()) {
                    users = userDetails.getSuccessDataOrNull()
                    _state.update {
                        it.copy(
                            users = users ?: Users(),
                            name = users?.name ?: "",
                            bio = users?.bio ?: "",
                            image = users?.imageUrl?.toUri(),
                            selectedGender = users?.gender ?: "",
                            date_of_birth = users?.dateOfBirth ?: "",
                        )
                    }
                } else {
                    showSnackbar(userDetails.getErrorData())
                }
            }
        }
    }


    fun onEvents(event: SettingsEvent) {
        when (event) {
            SettingsEvent.onAboutClick -> {}
            SettingsEvent.onChangeProfilePicture -> {

            }
            SettingsEvent.onIsExpandedChange -> {
                _state.update {
                    it.copy(
                        isexpanded = !state.value.isexpanded
                    )
                }
            }
            SettingsEvent.onIsOpenChange -> {
                _state.update {
                    it.copy(
                        isopen = !state.value.isopen
                    )
                }
            }
            SettingsEvent.saveChanges -> saveChanges()
            is SettingsEvent.onBioChange -> {
                _state.update {
                    it.copy(
                        bio = event.bio
                    )
                }
            }
            is SettingsEvent.onNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }
            is SettingsEvent.onSelectDate -> {
                _state.update {
                    it.copy(
                        date_of_birth = event.selectedDate
                    )
                }
            }
            is SettingsEvent.onSelectGender -> {
                _state.update {
                    it.copy(
                        selectedGender = event.gender
                    )
                }
            }

            is SettingsEvent.onSelectImage -> {
                _state.update {
                    it.copy(
                        image = event.image
                    )
                }
            }
        }
    }

    private fun saveChanges() {
        viewModelScope.launch {
            val state = state.value
            userRepository.updateUser(
                Users(
                    name = state.name,
                    bio = state.bio,
                    dateOfBirth = state.date_of_birth,
                    gender = state.selectedGender,
                    archivedUsers = state.users.archivedUsers,
                    connectedUsers = state.users.connectedUsers,
                    blockedUsers = state.users.blockedUsers,
                    uid = state.users.uid,
                    active = state.users.active,
                    imageUrl = if (state.image != null) state.image.toString() else state.users.imageUrl
                )
            )
        }
    }

}