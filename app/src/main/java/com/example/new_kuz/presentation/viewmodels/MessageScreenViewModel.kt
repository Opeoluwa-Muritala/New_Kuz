package com.example.new_kuz.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.new_kuz.domain.modules.Messages
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.presentation.events.MessageScreenEvent
import com.example.new_kuz.presentation.states.MessageScreenState
import com.example.new_kuz.util.changeMillisToDateString
import com.example.new_kuz.util.changeMillisToTimeString
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Comparator
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MessageScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(MessageScreenState())
    val state = _state.asStateFlow()
    private val reciever:String? = savedStateHandle["receiver"]


    val user = auth.currentUser?.uid

    init {
        getMessages()
        getRecieverDetails()
    }


    private fun getRecieverDetails(){
        db.collection("Users").document("${reciever}").get()
            .addOnSuccessListener { documentSnapshots ->
                val userDetails = documentSnapshots.toObject(Users::class.java)
                _state.update {
                    it.copy(
                        receiver = userDetails
                    )
                }
            }
    }

    private fun getMessages() {
        val messages = mutableListOf<Messages>()

        db.collection("Messages").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                for (document in value) {
                    messages.add(document.toObject(Messages::class.java))
                }
                val groupedMessages = messages
                    .filter { message -> message.sentto == user || message.sentby == user }
                    .filter { message -> message.sentto == reciever || message.sentby == reciever }
                    .groupBy { it.date }
                    .mapValues { entry->
                        entry.value.sortedByDescending { it.timeline }
                    }
                    .toSortedMap(Comparator.reverseOrder())
                    .flatMap { it.value }


                _state.update {
                    it.copy(
                        currentUser = user!!,
                        messages = groupedMessages
                    )
                }
            }
        }
    }

    fun onEvent(event: MessageScreenEvent) {
        when (event) {
            is MessageScreenEvent.SelectPhoto -> {
                _state.update {
                    it.copy(
                        images = event.photo?.map { it.toString() }!!
                    )
                }
            }


            MessageScreenEvent.onBackClick -> {}
            MessageScreenEvent.onBottomSheetStateChange -> {
                _state.update {
                    it.copy(
                        showBottomSheet = !state.value.showBottomSheet
                    )
                }
            }

            is MessageScreenEvent.onConfirmSheetEvent -> {
                when (event.event) {
                    "Archive" -> {}
                    "Block" -> {}
                    "Clear" -> {}
                    else -> {}
                }
            }

            is MessageScreenEvent.onContactClick -> {}
            MessageScreenEvent.onMenuStateChange -> {
                _state.update {
                    it.copy(
                        expandMenuBar = !state.value.expandMenuBar
                    )
                }
            }

            is MessageScreenEvent.onSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            MessageScreenEvent.onShowSearchBarChange -> {
                _state.update {
                    it.copy(
                        showSearchBar = !state.value.showSearchBar
                    )
                }
            }

            is MessageScreenEvent.onTaskChange -> {
                _state.update {
                    it.copy(
                        bottomSheetTask = event.task
                    )
                }
            }

            is MessageScreenEvent.onMessageStateChange -> {
                _state.update {
                    it.copy(
                        message = event.message
                    )
                }
            }

            MessageScreenEvent.sendMessage -> {
                sendMessages()
                _state.update {
                    it.copy(
                        message = "",
                        sendPhoto = emptyList(),
                        images = emptyList()
                    )
                }
            }
        }
    }

    private fun sendMessages() {
        val time = Calendar.getInstance(Locale.getDefault()).time
        val formattedTime = time.toInstant().toEpochMilli()
        uploadPhotos()
        db.collection("Messages").add(
            Messages(
                sentto = reciever!!,
                sentby = state.value.currentUser,
                message = state.value.message,
                date = formattedTime.changeMillisToDateString(),
                timeline = formattedTime.changeMillisToTimeString(),
                images = state.value.images
            )
        )
    }

    private fun uploadPhotos() {

        if (state.value.images.isNotEmpty()) {
            state.value.images.forEach { uri ->
                val imageuid = UUID.randomUUID()
                val storageRef = storage.reference
                val fileRef = storageRef.child("messageImg/${imageuid}.jpg")
                val uploadTask = fileRef.putFile(uri.toUri())
                val urlTask = uploadTask.continueWithTask(
                    Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        return@Continuation fileRef.downloadUrl
                    }
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUrls = mutableListOf<String>()
                        downloadUrls.add(task.result.toString())

                        _state.update {
                            it.copy(
                                images = downloadUrls
                            )
                        }
                    }
                }

                uploadTask.addOnSuccessListener { }
                urlTask.addOnSuccessListener { }

            }
        }
    }
}