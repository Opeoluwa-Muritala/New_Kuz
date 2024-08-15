package com.example.new_kuz.presentation.states

data class SignInState(
    val email: String = "",
    val passwordChangeEmail: String = "",
    val password: String  = "",
    val newpassword: String = "",
    val confirmPassword: String = "",
    val checked : Boolean = false
)