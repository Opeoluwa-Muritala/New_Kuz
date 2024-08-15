package com.example.new_kuz.presentation.screens.auth.signup.userdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.presentation.viewmodels.SignUpViewModel
import com.example.new_kuz.presentation.screens.auth.components.verifyScreen

@Composable
fun VerifyEmail(
    navController: NavController,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit
    ) {
    verifyScreen(
        text = state.emailOtp,
        valueChange ={onEvent(SignUpEvents.onEmailOtpChange(it))},
        header = "Verify Email",
        buttonClick = {
            onEvent(SignUpEvents.otpCompleteEmail)
            navController.navigate(authRoute.phoneNumber.route)
                      },
        description ="We have sent a pin to your email. Please enter the Pin below to continue.",
        navController = navController,
        image = null,
    )
}