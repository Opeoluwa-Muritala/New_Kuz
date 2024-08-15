package com.example.new_kuz.presentation.screens.auth.signup.userdetails

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.events.SignUpEvents
import com.example.new_kuz.presentation.states.SignUpState
import com.example.new_kuz.util.SnackBarEvent
import com.example.new_kuz.util.ValidateUserDataUseCase
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePassword(
    navController: NavController,
    state: SignUpState,
    snackBarEvent: SharedFlow<RequestState<String>>,
    onEvent: (SignUpEvents) -> Unit
) {
val context = LocalContext.current
var passwordError by remember {
    mutableStateOf<String?>(null)
}
passwordError = when {
    state.password.isBlank() || state.confirmPassword.isBlank() -> "Please enter password"
    state.password != state.confirmPassword -> "Password does not match"
    else -> "Password Match"
}
val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = true) {
        snackBarEvent.collectLatest { event ->
            when (event){
                RequestState.Idle -> {}
                RequestState.Loading -> {}
                is RequestState.Error -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_LONG).show()
                }
                is RequestState.Success -> {
                    Toast.makeText(context,event.data,Toast.LENGTH_LONG).show()
                }
            }
        }
    }


Scaffold(
    topBar = {
        CreatePasswordTopBar(
            navController = navController,
            header = "Create New Password",
            description = label.details
        )
    }
) { paddingValues ->
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 10.dp)
    ) {
        appField(
            value = state.password,
            onValueChange = { onEvent(SignUpEvents.onPasswordChange(it)) },
            label = "New Password",
            error = ValidateUserDataUseCase().isValidPassword(state.password),
            placeholderText = "Enter Password"
        )
        appField(
            value = state.confirmPassword,
            onValueChange = { onEvent(SignUpEvents.onConfirmPasswordChange(it)) },
            label = "Confirm Password",
            error = passwordError != "Password Match",
            placeholderText = "Re-enter Password",
            supportingText = passwordError
        )

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = state.checked,
                onCheckedChange = { onEvent(SignUpEvents.onCheckedChange(it)) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Gray,
                    uncheckedColor = Color.Gray,
                    checkmarkColor = MaterialTheme.colorScheme.primary,
                ),
            )

            Text(
                text = buildAnnotatedString {
                    append("I agree with Kuz Privacy Policies and Terms of service")
                    addStyle(
                        SpanStyle(
                            fontWeight = FontWeight(500),
                            color = MaterialTheme.colorScheme.primary,

                            ),
                        38,
                        54
                    )
                    addStyle(
                        SpanStyle(
                            fontWeight = FontWeight(500),
                            color = MaterialTheme.colorScheme.primary,

                            ),
                        16,
                        33
                    )
                }, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onSecondary,

                    textAlign = TextAlign.Center,
                    letterSpacing = 0.42.sp,
                )
            )

        }
        filledButton(
            onButtonClick = {
                onEvent(SignUpEvents.onCompleteSignUpPhone)
            },
            text = "Continue",
            color = Color.White,
            buttonColor = MaterialTheme.colorScheme.primary,
            enabled = passwordError == "Password Match"
        )
    }
}



    if (state.showsheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                onEvent(SignUpEvents.CloseSheet)
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(30.dp),

        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .border(2.dp, Color.Black)
                )
                Text(text = "Congratulations", color = MaterialTheme.colorScheme.onSecondary)
                Text(text = "You have successfully created your account.", color = MaterialTheme.colorScheme.onSecondary)
                filledButton(
                    onButtonClick = {
                        navController.navigate(Graph.HOME)
                    },
                    text = "Chat Now",
                    color = Color.White,
                    buttonColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreatePasswordTopBar(
    navController: NavController,
    header: String,
    description: String
) {
    Column {

        TopAppBar(
            title = {

            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "back",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { navController.navigateUp() },
                )
            }

        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = header,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 30.sp
            )
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}