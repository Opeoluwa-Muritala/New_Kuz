package com.example.new_kuz.presentation.states

import androidx.compose.ui.text.input.TextFieldValue
import com.togitech.ccp.data.CountryData
import com.togitech.ccp.data.utils.getLibCountries

data class SignUpState(
    var email: String = "",
    var phonenumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val emailOtp: String = "",
    val phoneOtp: String = "",
    var isexpanded: Boolean = false,
    val checked: Boolean = false,
    val countries: List<CountryData> = getLibCountries(),
    var country: String = countries[0].cNames,
    var selectedCountry: CountryData = countries[0],
    var firstname : String = "",
    var lastname : String = "",
    var dateofbirth : String = "",
    var isOpen: Boolean = false,
    val showsheet: Boolean = false
) {

}
