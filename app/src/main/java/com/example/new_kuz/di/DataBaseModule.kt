package com.example.new_kuz.di

import android.app.Application
import androidx.room.Room
import com.example.new_kuz.database.AppDataBase
import com.example.new_kuz.database.dao.MessageDao
import com.example.new_kuz.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDataBase {
        return Room
            .databaseBuilder(
                application,
                AppDataBase::class.java,
                "KUZ_database.db"
            )
            .build()
    }


    @Provides
    @Singleton
    fun provideUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideMessagesDao(dataBase: AppDataBase): MessageDao {
        return dataBase.messagesDao()
    }
}