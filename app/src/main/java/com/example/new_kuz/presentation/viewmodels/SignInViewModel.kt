package com.example.new_kuz.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.presentation.events.SignInEvents
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.presentation.states.SignInState
import com.example.new_kuz.util.RequestState
import com.example.new_kuz.util.SnackBarEvent
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _snackbarEventFlow = MutableSharedFlow<RequestState<String>>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()

    private val _user = MutableStateFlow<Users?>(null)
    val user = _user.asStateFlow()


    fun onEvents(event: SignInEvents) {
        when (event){
            is SignInEvents.onCheckedChange -> {
                _state.update {
                    it.copy(
                        checked = !state.value.checked
                    )
                }
            }
            is SignInEvents.onEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignInEvents.onPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            is SignInEvents.onConfirmPasswordChange -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.password
                    )
                }
            }
            is SignInEvents.onNewPasswordChange -> {
                _state.update {
                    it.copy(
                        newpassword = event.password
                    )
                }
            }
            is SignInEvents.onChangePasswordEmailChange -> {
                _state.update {
                    it.copy(
                        passwordChangeEmail = event.email
                    )
                }
            }
            SignInEvents.googleSignInClick -> googleSignIn()
            SignInEvents.signInClick -> signInFirebase()

            SignInEvents.forgotPasswordClick -> forgotPassword()
            SignInEvents.signUpClick -> {}
        }
    }

    private fun forgotPassword() {
        auth.sendPasswordResetEmail(
            state.value.passwordChangeEmail
        ).addOnSuccessListener {
            viewModelScope.launch {
                _snackbarEventFlow.emit(RequestState.Success("A Password Reset Mail Has Been Sent."))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _snackbarEventFlow.emit(RequestState.Error(it.localizedMessage ?: "Unknown Error Occurred"))
            }
        }
    }

    private fun googleSignIn(){

    }

    private fun signInFirebase() {
        val message = mutableStateOf("")
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(
                state.value.email, state.value.password
            ).addOnSuccessListener { authResult ->
                viewModelScope.launch {
                _snackbarEventFlow.emit(
                    RequestState.Success("Successful SignIn")
                )
                }

                getUserDetails(
                    result = authResult,
                    success = {
                        viewModelScope.launch {
                            _snackbarEventFlow.emit(
                                RequestState.Success("Successful SignIn")
                            )
                        }
                    },
                    failure = {viewModelScope.launch {
                        _snackbarEventFlow.emit(
                            RequestState.Error(it.localizedMessage ?: "Unknown Error Encountered")
                        )
                    }

                    }
                )
            }.addOnFailureListener {
                viewModelScope.launch {
                    _snackbarEventFlow.emit(
                        RequestState.Error(it.localizedMessage ?: "Unknown Error Encountered")
                    )
                }

            }
        }

    }

    private fun googleSignUp(){

    }

    private fun getUserDetails(
        result: AuthResult,
        success: () -> Unit,
        failure: (Exception) -> Unit
    ){
        val results = result.user!!
        db.collection("users")
            .document(results.uid)
            .get()
            .addOnSuccessListener { user ->
                _user.value = user.toObject()
                success()
            }
            .addOnFailureListener {
                failure(it)
            }
    }
}