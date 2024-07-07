package com.santoshdevadiga.sampleapp.ui.listing.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.santoshdevadiga.sampleapp.R
import com.santoshdevadiga.sampleapp.databinding.FragmentListBinding
import com.santoshdevadiga.sampleapp.ui.listing.list.placeholder.PlaceholderContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var listViewModel: ListViewModel

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentListBinding.inflate(inflater)
        listViewModel=ViewModelProvider(this)[ListViewModel::class.java]

        // Set the adapter
        if (binding.root is RecyclerView) {
            with(binding.root) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = ListRecyclerViewAdapter(PlaceholderContent.ITEMS){
                    Log.i("TAG","Item clicked!!")
                    val flag=findNavController().navigate(R.id.action_listFragment_to_detailFragment)
                    Log.i("TAG","Is Navigate : ${flag}")
                }

            }
        }
        return binding.root
    }

}