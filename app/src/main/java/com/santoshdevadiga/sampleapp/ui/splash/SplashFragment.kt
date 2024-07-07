package com.santoshdevadiga.sampleapp.ui.splash

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.santoshdevadiga.sampleapp.R
import com.santoshdevadiga.sampleapp.databinding.FragmentSlideshowBinding
import com.santoshdevadiga.sampleapp.databinding.FragmentSplashBinding
import com.santoshdevadiga.sampleapp.ui.slideshow.SlideshowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         splashViewModel =
             ViewModelProvider(this)[SplashViewModel::class.java]

        binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler().postDelayed(Runnable {
            Log.i("TAG","Timer Off ||")
            redirect()

        },3000)

        return binding.root
    }

    fun redirect(){
        Log.i("TAG","Redirect Called ||")
       // val navController:NavController?= activity?.findNavController(R.id.nav_host_fragment_content_main)
        //navController?.navigate(R.id.loginFragment)

        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

       // findNavController().navigate(R.id.action_splashFragment_to_registrationFragment)
    }
}