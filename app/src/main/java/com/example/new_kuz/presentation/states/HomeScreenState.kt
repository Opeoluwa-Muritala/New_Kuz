package com.example.new_kuz.presentation.states

import android.graphics.Bitmap
import com.example.new_kuz.domain.modules.Users

data class HomeScreenState(
    val userName: String = "",
    val image: Bitmap? = null,
    val contacts: List<Users> = emptyList(),
    val query: String = "",
    val available: Boolean = false,
    val connected: List<String> = emptyList(),
    val users: Users= Users()
)
