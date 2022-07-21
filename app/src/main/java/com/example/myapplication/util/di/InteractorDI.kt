package com.example.myapplication.util.di

import com.example.myapplication.io.ApiInteractor
import com.example.myapplication.io.UserInteractorImp
import org.koin.dsl.module

/**
 * Created by Samira Salah
 */
val moduleInteractor = module {
    single { UserInteractorImp(get()) as ApiInteractor }
}
