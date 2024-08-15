package com.example.new_kuz.presentation.screens.auth.signin.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.presentation.events.SignInEvents
import com.example.new_kuz.presentation.viewmodels.SignInViewModel
import com.example.new_kuz.presentation.screens.components.appField
import com.example.new_kuz.presentation.screens.components.filledButton
import com.example.new_kuz.util.RequestState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun forgotPasword(navController: NavController) {

    val viewModel: SignInViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val onEvent = viewModel::onEvents

    LaunchedEffect(key1 = true) {
        viewModel.snackBarEvenFlow.collectLatest { event ->
            when (event){
                RequestState.Idle -> {}
                RequestState.Loading -> {}
                is RequestState.Error -> {
                    Toast.makeText(context,event.message, Toast.LENGTH_LONG).show()
                }
                is RequestState.Success -> {
                    navController.navigate(authRoute.signIn.route)
                    Toast.makeText(context,event.data, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold { paddingValues ->

        Column(
            Modifier.padding(paddingValues)
                .padding(vertical = 10.dp)

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
                Text(
                    text = "Password Reset",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 30.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.forgot_password),
                    contentDescription = "forgot password",
                    modifier = Modifier
                        .size(90.dp)
                )
                Text(
                    text = "Enter your email address to reset your password",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                appField(
                    value = state.passwordChangeEmail,
                    onValueChange = { onEvent(SignInEvents.onChangePasswordEmailChange(it)) },
                    label = "Email",
                    error = false,
                    placeholderText = "Enter Email"
                )
                Spacer(modifier = Modifier.height(20.dp))
                filledButton(
                    onButtonClick = {
                        navController.navigate(authRoute.signIn.route)
                    },
                    text = "Continue",
                    color = Color.White,
                    buttonColor = MaterialTheme.colorScheme.primary,
                    enabled = state.passwordChangeEmail.isNotBlank()
                )
            }
        }
    }
}