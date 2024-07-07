package com.santoshdevadiga.sampleapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santoshdevadiga.sampleapp.repository.local.AppData
import com.santoshdevadiga.sampleapp.repository.AppRepository
import com.santoshdevadiga.sampleapp.models.UserPostList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject  constructor(private val appRepository: AppRepository) :ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getUserPost()
        }
    }

    val userPost:LiveData<UserPostList>
        get() = appRepository.userPost

    fun getAppData():LiveData<AppData>{
        return appRepository.getAppData()
    }

    fun insertAppData(appData: AppData){
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.insertAppData(appData)
        }
    }
}