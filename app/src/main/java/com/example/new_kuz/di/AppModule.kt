package com.example.new_kuz.di

import com.example.new_kuz.database.repository.MessageInterfaceImpl
import com.example.new_kuz.database.repository.UserInterfaceImpl
import com.example.new_kuz.domain.repository.MessageInterface
import com.example.new_kuz.domain.repository.UserInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun userInterFace(db: FirebaseFirestore, auth: FirebaseAuth, storage: FirebaseStorage): UserInterface{
        return UserInterfaceImpl(db,storage,auth)
    }

    @Provides
    @Singleton
    fun messageInterFace(db: FirebaseFirestore, storage: FirebaseStorage): MessageInterface {
        return MessageInterfaceImpl(storage,db)
    }
}