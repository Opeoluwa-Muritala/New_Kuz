package com.example.new_kuz.presentation.viewmodels

import android.net.Uri
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
            var users: Users
            userdata.collect { userDetails ->
                if (userDetails.isSuccess()) {
                    users = userDetails.getSuccessData()
                    _state.update {
                        it.copy(
                            name = users.name ?: "",
                            bio = users.bio ?: "",
                            image = users.imageUrl?.toUri(),
                            selectedGender = users.gender,
                            date_of_birth = users.dateOfBirth,
                        )
                    }
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
        val state = state.value
        uploadProfilephoto()
        db.collection("Users").document("${currentUser?.uid}")
            .update(
                mapOf(
                    "imageUrl" to state.image,
                    "name" to state.name,
                    "bio" to state.bio,
                    "gender" to state.selectedGender,
                    "dateOfBirth" to state.date_of_birth
                )
            )
    }

    private fun uploadProfilephoto() {

        state.value.image?.let {uri ->
            val storageRef = storage.reference
            val fileRef = storageRef.child("profile/${currentUser?.uid}.jpg")
            val uploadTask = fileRef.putFile(uri)
            val urlTask = uploadTask.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                    task ->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }
            )?.addOnCompleteListener {task ->
                if (task.isSuccessful){
                    val downloadUrl = task.result
                    _state.update {
                        it.copy(
                            image = downloadUrl
                        )
                    }
                }
            }

            uploadTask.addOnSuccessListener {  }
            urlTask?.addOnSuccessListener {  }

        }




    }
}