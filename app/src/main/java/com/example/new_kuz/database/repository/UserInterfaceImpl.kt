package com.example.new_kuz.database.repository

import androidx.compose.runtime.mutableStateOf
import com.example.new_kuz.domain.modules.Users
import com.example.new_kuz.domain.repository.UserInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class UserInterfaceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
):UserInterface {
    override fun getUser(uid: String): Users? {
        var document: Users? = mutableStateOf<Users?>(null).value
        db.collection("Users")
            .document(uid)
            .get().addOnFailureListener {
                return@addOnFailureListener
            }
            .addOnSuccessListener {querySnapshot ->
                 document = querySnapshot.toObject()
            }
        return document
    }

    override fun getAllUsers(): List<Users> {
        var documents: List<Users> = mutableListOf<Users>()
        db.collection("Users")
            .addSnapshotListener { value, error ->
                if (error != null){
                    return@addSnapshotListener
                }
                if (value != null){
                    documents = value.toObjects()
                }
            }
        return documents
    }

    override fun getArchived(users: List<String>): List<Users> {
        return getAllUsers().filter { users.contains(it.uid) }
    }

    override fun getBlocked(users: List<String>): List<Users> {
        return getAllUsers().filter { users.contains(it.uid) }
    }

    override fun getConnected(users: List<String>): List<Users> {
        return getAllUsers().filter { users.contains(it.uid) }
    }

    override fun getNotConnected(users: List<String>): List<Users> {
        return getAllUsers().filter { users.contains(it.uid) }
    }

    override fun updateUser(user: Users) {
        db.collection("Users").document(user.uid).set(user)
    }

}