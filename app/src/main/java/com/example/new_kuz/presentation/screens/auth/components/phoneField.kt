package com.example.new_kuz.presentation.screens.auth.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.new_kuz.presentation.screens.components.appField
import com.togitech.ccp.data.CountryData
import com.togitech.ccp.data.utils.checkPhoneNumber
import com.togitech.ccp.data.utils.getNumberHint
import com.togitech.ccp.transformation.PhoneNumberTransformation

@Composable
fun phoneField(
    text: String,
    textChange: (String) -> Unit,
    selectedCountry: CountryData
) {
    val keyboardController = LocalTextInputService.current


    appField(
        value = text,
        onValueChange = textChange,
        error =checkPhoneNumber(text,"${selectedCountry.countryCode} $text",
                selectedCountry.countryCode
            ),
        placeholderText = stringResource(id = getNumberHint(selectedCountry.countryCode)),
        transform = PhoneNumberTransformation(selectedCountry.countryCode.uppercase()),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword,
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hideSoftwareKeyboard() }),
    )
}