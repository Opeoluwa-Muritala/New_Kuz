package com.example.new_kuz.util

class ValidateUserDataUseCase {
    fun isValidPassword(password: String): Boolean {
        val hasUpperCase = password.any{it.isUpperCase()}
        val hasLowerCase = password.any{it.isLowerCase()}
        val hasDigit = password.any{it.isDigit()}
        val hasValidLength = password.length >= 8

        return hasValidLength && hasDigit && hasUpperCase && hasLowerCase
    }
}