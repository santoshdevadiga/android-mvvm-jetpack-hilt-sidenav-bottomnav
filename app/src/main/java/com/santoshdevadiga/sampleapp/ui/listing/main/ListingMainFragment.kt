package com.santoshdevadiga.sampleapp.ui.listing.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.santoshdevadiga.sampleapp.databinding.FragmentListingMainBinding

class ListingMainFragment : Fragment() {

    private lateinit var binding: FragmentListingMainBinding
    private lateinit var viewModel: ListingMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentListingMainBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ListingMainViewModel::class.java)
        return binding.root
    }

}