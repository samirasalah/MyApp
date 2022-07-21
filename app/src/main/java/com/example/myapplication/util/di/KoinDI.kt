package com.example.myapplication.util.di

import com.example.myapplication.viewModel.FirstViewModel
import com.example.myapplication.viewModel.InitialViewModel
import com.example.myapplication.viewModel.MainActivityViewModel
import com.example.myapplication.viewModel.SecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Samira Salah
 */
val module = module {
    viewModel { MainActivityViewModel() }
    viewModel { FirstViewModel(get()) }
    viewModel { SecondViewModel() }
    viewModel { InitialViewModel() }
}