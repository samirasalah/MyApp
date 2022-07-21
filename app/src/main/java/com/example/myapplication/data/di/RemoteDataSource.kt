package com.example.myapplication.data.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient

/**
 * Created by Samira Salah
 */
val remoteDataSourceModule= module {
    single { provideGson() }
    single { provideRemoteDataSource(get()) }
}

fun provideGson(): Gson {
    return GsonBuilder().create()
}

fun provideRemoteDataSource(gson: Gson): ApiService {
    val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)

    clientBuilder.addInterceptor(interceptor = HttpInterceptor())

    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(interceptor).build()
    }

    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .client(clientBuilder.build())
        //.addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiService::class.java)
}