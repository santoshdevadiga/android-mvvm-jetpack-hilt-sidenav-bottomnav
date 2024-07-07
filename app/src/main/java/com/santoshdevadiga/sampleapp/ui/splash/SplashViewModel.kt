package com.santoshdevadiga.sampleapp.ui.splash

import androidx.lifecycle.ViewModel
import com.santoshdevadiga.sampleapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(appRepository: AppRepository) : ViewModel() {

}