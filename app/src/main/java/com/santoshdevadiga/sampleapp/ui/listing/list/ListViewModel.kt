package com.santoshdevadiga.sampleapp.ui.listing.list

import androidx.lifecycle.ViewModel
import com.santoshdevadiga.sampleapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val appRepository: AppRepository): ViewModel() {

}