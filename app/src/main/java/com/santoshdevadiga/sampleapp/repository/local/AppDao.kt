package com.santoshdevadiga.sampleapp.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  AppDao {
    @Query("SELECT * from app")
    fun getAppData():LiveData<AppData> //livedata return fun always run on background thread

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAppData(appData: AppData) //TODO suspend function not working in room database
}