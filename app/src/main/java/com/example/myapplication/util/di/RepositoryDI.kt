package com.example.myapplication.util.di

import com.example.myapplication.io.repository.UserRepository
import org.koin.dsl.module

/**
 * Created by Samira Salah
 */
val moduleRepository= module {
    single { UserRepository(get()) }
}