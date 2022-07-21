package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.di.remoteDataSourceModule
import com.example.myapplication.util.di.module
import com.example.myapplication.util.di.moduleInteractor
import com.example.myapplication.util.di.moduleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Samira Salah
 */
class App : Application() {
    companion object{
        lateinit var instance:App
    }

    override fun onCreate() {
        super.onCreate()
        instance=this

        startKoin {
            androidContext(this@App)
            modules (remoteDataSourceModule)
            modules(moduleInteractor)
            modules(moduleRepository)
            modules (module)
        }
    }
}