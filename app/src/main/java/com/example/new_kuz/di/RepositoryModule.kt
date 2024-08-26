package com.example.new_kuz.di

import com.example.new_kuz.database.repository.MessageRepositoryImpl
import com.example.new_kuz.database.repository.UserRepositoryImpl
import com.example.new_kuz.domain.repository.MessageRepository
import com.example.new_kuz.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindMessageRepository(
        impl: MessageRepositoryImpl
    ): MessageRepository
}