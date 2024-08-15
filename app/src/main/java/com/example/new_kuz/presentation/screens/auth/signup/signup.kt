package com.example.new_kuz.presentation.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.new_kuz.KUZTheme
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.util.SnackBarEvent
import com.example.new_kuz.presentation.screens.auth.components.Logo
import com.example.new_kuz.presentation.screens.auth.components.headerDetailText
import com.example.new_kuz.presentation.screens.components.GoogleButton
import com.example.new_kuz.presentation.screens.components.LabelledTextField
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpNavigation(
    navController: NavController,
    state: SignUpState,
    onEvents: (SignUpEvents) -> Unit,
    toastEvent: SharedFlow<RequestState<String>>
){

    KUZTheme {
        signUpUi(

            onEvent = {event ->
                when (event) {
                    SignUpEvents.googleSignUp -> {
                        onEvents(event)
                        navController.navigate(authRoute.phoneNumber.route)
                    }
                    SignUpEvents.onEnterEmail -> {
                        onEvents(event)
                        navController.navigate(authRoute.phoneNumber.route)
                    }
                    SignUpEvents.onSignInClick -> {navController.navigate(authRoute.signIn.route)}
                    else -> onEvents(event)
                }

            },
            state = state,
            snackBarEvent = toastEvent
        )
    }

}

@Composable
private fun signUpUi(
    snackBarEvent:  SharedFlow<RequestState<String>>,
    onEvent: (SignUpEvents) -> Unit,
    state: SignUpState,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackBarEvent.collectLatest { event ->
            when (event){
                RequestState.Idle -> {}
                RequestState.Loading -> {}
                is RequestState.Error -> {}
                is RequestState.Success -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {

        LazyColumn(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            item {
                Column(
                    Modifier
                        .fillMaxSize(0.8f)
                        .padding(bottom = 200.dp)
                    ,                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Header()
                    SignUp(
                        email = state.email,
                        onEmailChange = { onEvent(SignUpEvents.onEmailChange(it)) },
                        emailLabel = label.email,
                        onSignUpClick = { onEvent(SignUpEvents.onEnterEmail) }
                    )
                }
            }



            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GoogleButton(
                        onSignInClick = { onEvent(SignUpEvents.googleSignUp) },
                        text = "Up",
                        enabled = state.email != ""
                    )
                    Footer(
                        onSignInClick = { onEvent(SignUpEvents.onSignInClick) },
                        signInLabel = label.signin
                    )
                }

            }
        }
    }
}

@Composable
private fun Header(
    signupHeader: String = label.signupheader,
    details: String = label.details
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Logo(color = MaterialTheme.colorScheme.primary, fontSize = 90)
        Spacer(modifier = Modifier.height(20.dp))
        headerDetailText(
            text = signupHeader,
            details = details,
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
private fun SignUp(
    email: String,
    onEmailChange: (String) -> Unit,
    emailLabel: String,
    onSignUpClick: () -> Unit
) {
    Column(
        Modifier.fillMaxSize()

    ) {
        LabelledTextField(
            fieldText = email,
            title = emailLabel,
            onFieldTextChange = onEmailChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        filledButton(
            onButtonClick = onSignUpClick,
            text = "Continue",
            color = Color.White,
            buttonColor = MaterialTheme.colorScheme.primary,
            enabled = email != ""
        )
    }

}

@Composable
private fun Footer(
    onSignInClick: () -> Unit,
    signInLabel: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(text = "Don't have an account?", color = MaterialTheme.colorScheme.onSecondary)

        TextButton(onClick =  onSignInClick) {
            Text(
                text = signInLabel,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )
        }

    }
    Spacer(modifier = Modifier.height(90.dp))
}
