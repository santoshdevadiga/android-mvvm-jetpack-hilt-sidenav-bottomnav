package com.santoshdevadiga.sampleapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.santoshdevadiga.sampleapp.models.UserPostList
import com.santoshdevadiga.sampleapp.repository.local.AppDao
import com.santoshdevadiga.sampleapp.repository.local.AppData
import com.santoshdevadiga.sampleapp.repository.local.AppDatabase
import com.santoshdevadiga.sampleapp.repository.remote.AppServiceAPI
import javax.inject.Inject

class AppRepository @Inject constructor(private val appDatabase: AppDatabase, private val appServiceAPI: AppServiceAPI) {

    fun getAppData():LiveData<AppData>{
        return appDatabase.appDao().getAppData()
    }

    fun insertAppData(appData: AppData){
       return  appDatabase.appDao().insertAppData(appData)
    }

    private val userPostLiveData= MutableLiveData<UserPostList>()
    val userPost:LiveData<UserPostList>
        get() = userPostLiveData


    suspend fun getUserPost(){
        val result =appServiceAPI.getUserPostList()
        if(result.body() != null){
            userPostLiveData.postValue(result.body())
        }
    }
}