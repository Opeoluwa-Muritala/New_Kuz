package com.example.new_kuz.domain.repository

import com.example.new_kuz.domain.modules.Users

interface UserInterface {
    fun getUser(uid: String): Users?
    fun getAllUsers():List<Users>
    fun getArchived(users: List<String>):List<Users>
    fun getBlocked(users: List<String>):List<Users>
    fun getConnected(users: List<String>):List<Users>
    fun getNotConnected(users: List<String>):List<Users>
    fun updateUser(user: Users)
}