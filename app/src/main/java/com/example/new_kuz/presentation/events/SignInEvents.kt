package com.example.new_kuz.presentation.events

sealed class SignInEvents {
    data class onEmailChange(val email: String) : SignInEvents()
    data class onPasswordChange(val password: String) : SignInEvents()
    data class onNewPasswordChange(val password: String) : SignInEvents()
    data class onConfirmPasswordChange(val password: String) : SignInEvents()
    data class onChangePasswordEmailChange(val email: String) : SignInEvents()
    data class onCheckedChange(val checked: Boolean) : SignInEvents()
    data object googleSignInClick: SignInEvents()
    data object signInClick: SignInEvents()
    data object signUpClick: SignInEvents()
    data object forgotPasswordClick: SignInEvents()
}