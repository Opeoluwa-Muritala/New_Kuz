package com.example.new_kuz.presentation.screens.auth.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.GoogleButton
import com.example.new_kuz.presentation.screens.auth.components.Logo
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.presentation.screens.auth.components.headerDetailText
import com.example.new_kuz.presentation.viewmodels.SignInViewModel
import com.example.new_kuz.presentation.events.SignInEvents
import com.example.new_kuz.presentation.states.SignInState
import com.example.new_kuz.util.SnackBarEvent
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.presentation.screens.components.LabelledTextField
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun signInNavigation(navController: NavController){

    val viewModel: SignInViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(true){
        viewModel.snackBarEvenFlow.collectLatest {request ->
            when (request){
                RequestState.Idle -> {}
                RequestState.Loading -> {}
                is RequestState.Error -> {
                    Toast.makeText(context,request.message,Toast.LENGTH_LONG).show()
                }
                is RequestState.Success -> {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                    Toast.makeText(context,request.data,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    signInUi(
        state = state,
        onEvent = {event ->
            when (event) {
                SignInEvents.signInClick -> {
                    viewModel.onEvents(event)
                    //
                }
                SignInEvents.googleSignInClick -> {

                }
                SignInEvents.signUpClick -> {
                    navController.navigate(Graph.SIGN_UP)
                }
                SignInEvents.forgotPasswordClick -> {
                    navController.navigate(authRoute.forgotPassword.route)
                }
                else -> viewModel.onEvents(event)
            }
        },
    )
}

@Composable
private fun signInUi(
    state: SignInState,
    onEvent: (SignInEvents)-> Unit
) {


    Scaffold(

    ) {
        LazyColumn(
            Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Header()
            }
            item {
                signInFields(
                    emailLabel = label.email,
                    email = state.email,
                    passwordLabel = label.password,
                    password = state.password,
                    onPasswordChange = { onEvent(SignInEvents.onPasswordChange(it)) },
                    onEmailChange = {onEvent(SignInEvents.onEmailChange(it))},
                )
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(checked = state.checked, onCheckedChange = {onEvent(SignInEvents.onCheckedChange(it))})
                    Text(text = "Keep me logged in", color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            item{
                Column{
                    ForgotPassword(onForgotPasswordClick = { onEvent(SignInEvents.forgotPasswordClick) })
                    buttons(
                        googleSignInButton = { onEvent(SignInEvents.googleSignInClick) },
                        signInButton = { onEvent(SignInEvents.signInClick) },
                        buttonLabel = label.signin,
                        enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
                    )
                }
            }
            item {
                Footer(
                    signUp = label.signup,
                    onSignUpClick = {onEvent(SignInEvents.signUpClick)}
                )
            }
        }
    }
}

@Composable
private fun Header(
    signInHeader: String = label.signinheader,
    details: String = label.details
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Logo(color = MaterialTheme.colorScheme.primary, fontSize = 90)
        Spacer(modifier = Modifier.height(20.dp))
        headerDetailText(text = signInHeader, details = details)
        Spacer(modifier = Modifier.height(20.dp))
    }
}



@Composable
private fun signInFields(
    email: String,
    password: String,
    emailLabel: String = "Email",
    passwordLabel: String = "Password",
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
){
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }
    passwordError = when {
        password.isBlank() -> "Please enter password"
        password.length < 8 -> "Minimum of 8 characters in password"
        else -> null
    }
    Column{
        LabelledTextField(
            fieldText = email,
            title = emailLabel,
            onFieldTextChange = onEmailChange
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Text(text = passwordLabel, color = MaterialTheme.colorScheme.onSecondary)
            appField(
                value = password,
                onValueChange = onPasswordChange,
                error = passwordError != null,
                placeholderText = "Enter $passwordLabel",
                transform = PasswordVisualTransformation(),
                supportingText = passwordError
            )
        }


    }
}

@Composable
private fun buttons(
    signInButton: () -> Unit,
    googleSignInButton: () -> Unit,
    buttonLabel: String,
    enabled: Boolean
){
    Column(
        Modifier
            .padding(horizontal = 10.dp)
    ) {

        filledButton(
            onButtonClick = signInButton,
            text = buttonLabel,
            color = Color.White,
            buttonColor = MaterialTheme.colorScheme.primary,
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(10.dp))
        GoogleButton (
            onSignInClick = googleSignInButton,
            text = "In",
            enabled = enabled
        )
    }
}

@Composable
private fun ForgotPassword(
    onForgotPasswordClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(30.dp))
    Row {
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onForgotPasswordClick
        ) {
            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
private fun Footer(
    signUp: String,
    onSignUpClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier.padding(top= 8.dp)
    ) {
        Text(text = "Don't have an account?", color = MaterialTheme.colorScheme.onSecondary)

        TextButton(onClick = onSignUpClick) {

            Text(
                text = signUp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )
        }
    }
    Spacer(modifier = Modifier.height(50.dp))
}