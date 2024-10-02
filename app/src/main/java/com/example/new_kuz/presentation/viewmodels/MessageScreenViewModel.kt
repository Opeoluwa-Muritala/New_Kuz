package com.example.new_kuz.presentation.viewmodels

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MessageScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(MessageScreenState())
    val state = _state.onEach { getMessages() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        MessageScreenState()
    )
    private val receiver: String? = savedStateHandle["receiver"]


    val user = auth.currentUser?.uid

    init {
        getMessages()
        getRecieverDetails()
    }


    private fun getRecieverDetails() {
        db.collection("Users").document("${receiver}").get()
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
        val dates = mutableListOf<String>()
        val messages = mutableListOf<Messages>()

        db.collection("Messages").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                val tmp = value.toObjects(Messages::class.java)

                tmp.filter { message -> message.sentto == user || message.sentby == user }
                    .filter { message -> message.sentto == receiver || message.sentby == receiver }
                    .forEach {
                        val tmpimg = mutableListOf<String>()
                        if (it.images.isNotEmpty()){
                            runBlocking(Dispatchers.IO){
                                it.images.forEach {imgUrl ->
                                    val ref = storage.reference.child(imgUrl)
                                    val ONE_MEGABYTES: Long = 1024 *1024
                                    ref.getBytes(ONE_MEGABYTES).addOnSuccessListener {
                                        tmpimg.add(BitmapFactory.decodeByteArray(it, 0, it.size).toString())
                                    }
                                }
                            }
                        }

                        if (!dates.contains(it.date)) {
                            dates.add(it.date)
                            dates.sort()
                        }
                        messages.add(
                            it.copy(
                                images = tmpimg
                            )
                        )
                    }
                val groupedMessages = messages
                    .filter { message -> message.sentto == user || message.sentby == user }
                    .filter { message -> message.sentto == receiver || message.sentby == receiver }
                    .groupBy { it.date }
                    .mapValues { entry ->
                        entry.value.sortedByDescending { it.timeline }.reversed()
                    }.toSortedMap(reverseOrder())

                _state.update {
                    it.copy(
                        currentUser = user!!,
                        messages = groupedMessages,
                        dates = dates
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
                        images = event.photo?.map { it.toString() }!!,
                        sendPhoto = event.photo
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
                    "Archive" -> archiveContact()
                    "Block" -> blockContact()
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

    private fun archiveContact() {
        val tmpData = mutableListOf<String>()
        db.collection("Users").document(user!!).get()
            .addOnSuccessListener {
                val oldUserData = it.toObject(Users::class.java)

                tmpData.addAll(oldUserData?.archivedUsers!!)
                if (!tmpData.contains(state.value.receiver?.uid)) {
                    state.value.receiver?.uid?.let { it1 -> tmpData.add(it1) }
                }
                db.collection("Users").document(user).update(
                    mapOf("archivedUsers" to tmpData.toList())
                )
            }
    }

    private fun blockContact() {
        val tmpData = mutableListOf<String>()
        db.collection("Users").document(user!!).get()
            .addOnSuccessListener {
                val oldUserData = it.toObject(Users::class.java)

                tmpData.addAll(oldUserData?.blockedUsers!!)
                if (!tmpData.contains(state.value.receiver?.uid)) {
                    state.value.receiver?.uid?.let { it1 -> tmpData.add(it1) }
                }
                db.collection("Users").document(user).update(
                    mapOf("blockedUsers" to tmpData.toList())
                )
            }
    }

    private fun sendMessages() {
        val time = Calendar.getInstance(Locale.getDefault()).time
        val formattedTime = time.toInstant().toEpochMilli()
        uploadPhotos()
        db.collection("Messages").add(
            Messages(
                sentto = receiver!!,
                sentby = state.value.currentUser,
                message = state.value.message,
                date = formattedTime.changeMillisToDateString(),
                timeline = formattedTime.changeMillisToTimeString(),
                images = state.value.images
            )
        )
        _state.update {
            it.copy(
                images = emptyList(),
                sendPhoto = emptyList()
            )
        }
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