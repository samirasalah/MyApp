package com.example.myapplication.activity

import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewModel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Samira Salah
 */
class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {
    override val mViewModel: MainActivityViewModel by viewModel ()
    override fun getLayoutResId(): Int =R.layout.activity_main

}