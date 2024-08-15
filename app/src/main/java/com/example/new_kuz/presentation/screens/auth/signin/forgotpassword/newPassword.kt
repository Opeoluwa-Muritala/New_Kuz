package com.example.new_kuz.presentation.screens.auth.signin.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.domain.modules.label
import com.example.new_kuz.presentation.events.SignInEvents
import com.example.new_kuz.presentation.viewmodels.SignInViewModel
import com.example.new_kuz.navigation.Graph
import com.example.new_kuz.presentation.screens.auth.components.headerDetailText
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.filledButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newPassword(navController: NavController) {

    val viewModel: SignInViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val onEvent = viewModel::onEvents
    val sheetState = rememberModalBottomSheetState()
    var show by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }
    passwordError = when {
        state.newpassword.isBlank() || state.confirmPassword.isBlank() -> "Please enter password"
        state.newpassword != state.confirmPassword -> "Password does not match"
        else -> "Password Match"
    }
    Scaffold { paddingValues ->

        Column(
            Modifier.padding(paddingValues)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "back",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigateUp() },
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                headerDetailText(
                    text = "Create New Password",
                    details = label.details,
                )
                appField(
                    value = state.newpassword,
                    onValueChange = { onEvent(SignInEvents.onNewPasswordChange(it)) },
                    label = "New Password",
                    error = false,
                    placeholderText = "Enter Password"
                )
                appField(
                    value = state.confirmPassword,
                    onValueChange = {
                        onEvent(SignInEvents.onConfirmPasswordChange(it))
                    },
                    label = "Confirm Password",
                    error = passwordError != "Password Match",
                    placeholderText = "Re-enter Password",
                    supportingText = passwordError
                )


                Spacer(modifier = Modifier.height(30.dp))
                filledButton(
                    onButtonClick = {
                        navController.navigate(Graph.HOME)
                    },
                    text = "Continue",
                    color = Color.White,
                    buttonColor = MaterialTheme.colorScheme.primary,
                    enabled = passwordError == "Password Match"
                )
            }
        }


        if (show) {
            ModalBottomSheet(
                onDismissRequest = {
                    show = false
                },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(30.dp)
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
                    Text(text = "Congratulations")
                    Text(text = "You have successfully created updated your password.")
                    filledButton(
                        onButtonClick = { navController.navigate(Graph.HOME) },
                        text = "Chat Now",
                        color = Color.White,
                        buttonColor = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}