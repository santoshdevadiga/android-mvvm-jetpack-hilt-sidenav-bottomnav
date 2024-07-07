package com.santoshdevadiga.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.santoshdevadiga.sampleapp.repository.local.AppDao
import com.santoshdevadiga.sampleapp.repository.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(context,
            AppDatabase::class.java,
            "app_database")
            .build()
    }
}