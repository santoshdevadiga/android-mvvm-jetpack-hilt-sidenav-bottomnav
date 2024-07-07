package com.santoshdevadiga.sampleapp.ui.listing.detail

import androidx.lifecycle.ViewModel
import com.santoshdevadiga.sampleapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

}