package com.santoshdevadiga.sampleapp.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppData::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {
    abstract fun appDao():AppDao
}