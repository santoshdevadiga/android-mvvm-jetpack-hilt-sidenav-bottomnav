package com.santoshdevadiga.sampleapp.repository.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "app",indices = [Index(value = ["userID"],
    unique = true)])
data class AppData (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val isFirstLogin:Boolean?,
    val isUserLoggedIn:Boolean?,
    //@Ignore val picture: Bitmap?
    val userID:String?
)