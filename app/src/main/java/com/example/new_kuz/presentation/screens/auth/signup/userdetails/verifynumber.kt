package com.example.new_kuz.presentation.screens.auth.signup.userdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.screens.auth.components.verifyScreen
import com.example.new_kuz.presentation.viewmodels.SignUpViewModel
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState

@Composable
fun VerifyNumber(
    navController: NavController,
    onEvent: (SignUpEvents) -> Unit,
    state: SignUpState
) {
    verifyScreen(
        text =  state.phoneOtp,
        valueChange = {
            onEvent(SignUpEvents.onPhoneOtpChange(it.toString()))
        },
        header = "Phone Number",
        buttonClick = {
            onEvent(SignUpEvents.otpCompletePhone)
            navController.navigate(authRoute.personalInfo.route)
                      },
        description = label.details,
        navController = navController,
        image = null,
    )
}