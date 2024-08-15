package com.example.new_kuz.presentation.events

import com.togitech.ccp.data.CountryData

sealed class SignUpEvents {
    data class onEmailChange(val email: String): SignUpEvents()
    data class onPhoneChange(val phone: String): SignUpEvents()
    data class onFirstNameChange(val firstname: String): SignUpEvents()
    data class onLastNameChange(val lastname: String): SignUpEvents()
    data class onSelectedDateChange(val dateOfBirth: String): SignUpEvents()
    data class onPasswordChange(val password: String): SignUpEvents()
    data class onConfirmPasswordChange(val confirmPassword: String): SignUpEvents()
    data class onEmailOtpChange(val emailOtp: String): SignUpEvents()
    data class onPhoneOtpChange(val phoneOtp: String): SignUpEvents()
    data class onExpandedChange(val isexpanded: Boolean): SignUpEvents()
    data class onCheckedChange(val checked: Boolean): SignUpEvents()
    data class onSelectedCountry(val selectedCountry: CountryData): SignUpEvents()
    data class datePickerStateChange(val state: Boolean): SignUpEvents()
    data object googleSignUp: SignUpEvents()
    data object onEnterEmail: SignUpEvents()
    data object onEnterPhone: SignUpEvents()
    data object otpCompleteEmail: SignUpEvents()
    data object otpCompletePhone: SignUpEvents()
    data object onCompleteSignUpPhone: SignUpEvents()
    data object onSignInClick: SignUpEvents()
    object CloseSheet : SignUpEvents()
}