package com.example.new_kuz.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.util.RequestState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()




    private val _snackbarEventFlow = MutableSharedFlow<RequestState<String>>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()


    fun onEvents(event: SignUpEvents) {
        when (event){
            is SignUpEvents.onEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignUpEvents.onSelectedDateChange -> {
                _state.update {
                    it.copy(
                        dateofbirth = event.dateOfBirth
                    )
                }
            }
            is SignUpEvents.onFirstNameChange -> {
                _state.update {
                    it.copy(
                        firstname = event.firstname
                    )
                }
            }
            is SignUpEvents.onLastNameChange -> {
                _state.update {
                    it.copy(
                        lastname = event.lastname
                    )
                }
            }
            is SignUpEvents.onEmailOtpChange -> {
                _state.update {
                    it.copy(
                        emailOtp = event.emailOtp
                    )
                }
            }
            is SignUpEvents.onPhoneChange -> {
                _state.update {
                    it.copy(
                        phonenumber = event.phone
                    )
                }
            }
            is SignUpEvents.onPhoneOtpChange -> {
                _state.update {
                    it.copy(
                        phoneOtp = event.phoneOtp
                    )
                }
            }
            is SignUpEvents.onConfirmPasswordChange -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
            }
            is SignUpEvents.onPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            is SignUpEvents.onExpandedChange -> {
                _state.update {
                    it.copy(
                        isexpanded = !state.value.isexpanded
                    )
                }
            }
            is SignUpEvents.onCheckedChange -> {
                _state.update {
                    it.copy(
                        checked = !state.value.checked
                    )
                }
            }
            is SignUpEvents.onSelectedCountry -> {
                _state.update {
                    it.copy(
                        country = event.selectedCountry.cNames,
                        selectedCountry = event.selectedCountry
                    )
                }
            }
            is SignUpEvents.datePickerStateChange -> {
                _state.update {
                    it.copy(
                        isOpen = !event.state
                    )
                }
            }
            SignUpEvents.googleSignUp -> googleSignUp()
            SignUpEvents.onEnterEmail -> {}
            SignUpEvents.onEnterPhone -> {
                _state.update {
                    it.copy(
                        phonenumber = state.value.selectedCountry.countryPhoneCode + state.value.phonenumber
                    )
                }
            }
            SignUpEvents.otpCompleteEmail -> {}
            SignUpEvents.otpCompletePhone -> signUpPhone()
            SignUpEvents.onSignInClick -> {}
            SignUpEvents.onCompleteSignUpPhone -> signUpFirebase()
            SignUpEvents.CloseSheet -> {
                _state.update {
                    it.copy(
                        showsheet = !state.value.showsheet
                    )
                }
            }
        }
    }

    private fun signUpPhone() {
//        val options = PhoneAuthOptions.newBuilder()
//            .setPhoneNumber("${state.value.selectedCountry.countryPhoneCode}${state.value.phonenumber}")
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setCallbacks()
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signUpFirebase() {

        viewModelScope.launch {
            var tmpMessage = mutableStateOf("")
            auth.createUserWithEmailAndPassword(
                state.value.email, state.value.password
            ).addOnSuccessListener { authResult ->
                _state.update {
                    it.copy(
                        showsheet = !state.value.showsheet
                    )
                }
                authResult.user?.sendEmailVerification()

                getUserDetails(
                    result = authResult,
                    success = {

                    },
                    failure = {
                    }
                )
                viewModelScope.launch {
                    _snackbarEventFlow.emit(RequestState.Success("You Have Been Signed Up"))
                }
            }
                .addOnFailureListener {
                    tmpMessage.value = it.localizedMessage.orEmpty()
                    viewModelScope.launch {
                        _snackbarEventFlow.emit(RequestState.Error(tmpMessage.value))
                    }
            }
            if (auth.currentUser?.isEmailVerified == true){
                 _snackbarEventFlow.emit(RequestState.Success("Thank You For Signing Up For Kuz "))
            } else {
                _snackbarEventFlow.emit(RequestState.Error("You Have Not Verified Your Email"))
                auth.currentUser?.sendEmailVerification()
                _snackbarEventFlow.emit(RequestState.Error("Please Check Your Email For Verification"))
            }
        }

    }

    private fun googleSignUp(){

    }

    private fun getUserDetails(
        result: AuthResult,
        success: () -> Unit,
        failure: () -> Unit
    ){
        val results = result.user!!
        val state = state.value
        val user = Users(
            uid = results.uid,
            bio = "",
            name = "${state.firstname} ${state.lastname}",
            imageUrl = "",
            active = true,
            dateOfBirth = state.dateofbirth,
            blockedUsers = emptyList(),
            archivedUsers = emptyList(),
            connectedUsers = emptyList(),
        )
        db.collection("Users")
             .document(results.uid)
             .set(user)
            .addOnSuccessListener {success()}
            .addOnFailureListener {failure()}
    }
}