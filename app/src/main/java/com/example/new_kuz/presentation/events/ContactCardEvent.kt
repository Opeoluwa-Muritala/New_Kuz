package com.example.new_kuz.presentation.events

sealed class ContactCardEvent {
    data object blockContact: ContactCardEvent()
    data object archiveContact: ContactCardEvent()
    data object onBackClick: ContactCardEvent()
}