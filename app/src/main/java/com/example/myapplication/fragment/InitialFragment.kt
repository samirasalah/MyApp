package com.example.myapplication.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentInitialBinding
import com.example.myapplication.viewModel.InitialViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Samira Salah
 */
class InitialFragment : BaseFragment<FragmentInitialBinding, InitialViewModel>() {
    override val mViewModel: InitialViewModel by viewModel()
    override fun getLayoutResId(): Int = R.layout.fragment_initial

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        // Post delayed for splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(InitialFragmentDirections.actionInitialFragmentToFirstFragment())
        }, 2000)
    }


}