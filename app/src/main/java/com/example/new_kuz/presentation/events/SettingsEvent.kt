package com.example.new_kuz.presentation.events

import android.net.Uri

sealed class SettingsEvent {
    data object onIsOpenChange: SettingsEvent()
    data object onIsExpandedChange: SettingsEvent()
    data object onChangeProfilePicture: SettingsEvent()
    data object saveChanges: SettingsEvent()
    data object onAboutClick: SettingsEvent()
    data class onSelectDate(val selectedDate: String): SettingsEvent()
    data class onNameChange(val name: String): SettingsEvent()
    data class onSelectGender(val gender: String): SettingsEvent()
    data class onBioChange(val bio: String): SettingsEvent()
    data class onSelectImage(val image: Uri?): SettingsEvent()
}