package com.example.new_kuz.presentation.states

import android.net.Uri
import androidx.compose.runtime.Stable

data class SettingsState(
    val name: String = "",
    var selectedGender: String = "Select A Gender",
    var date_of_birth: String = "Select A Date Of Birth e.g 11/02/2999",
    var bio: String = "",
    var isexpanded: Boolean = false,
    var isopen: Boolean = false,
    val genders: List<String> = Genders.genders,
    val image: Uri? = null
){
    @Stable
    data object Genders {
        val genders = listOf("Male", "Female", "Others")
    }
}

