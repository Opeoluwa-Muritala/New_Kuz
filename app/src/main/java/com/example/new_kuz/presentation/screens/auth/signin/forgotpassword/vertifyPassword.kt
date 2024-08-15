package com.example.new_kuz.presentation.screens.auth.signin.forgotpassword


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.new_kuz.R
import com.example.new_kuz.domain.modules.authRoute
import com.example.new_kuz.presentation.screens.auth.components.verifyScreen

@Composable
fun verifyPin(navController: NavController) {
    var field by remember {
        mutableStateOf("")
    }
    verifyScreen(
        text = field,
        valueChange = { field = it },
        header = "Verify Pin",
        description = "We have sent you a password reset pin to your email. Please enter the pin below to continue",
        buttonClick = {
            navController.navigate(authRoute.newPassword.route)
        },
        navController = navController,
        image = R.drawable.verify_pin,
    )
}