package com.example.myapplication.data.di


import com.example.myapplication.data.di.exception.ProjectExceptions
import com.example.myapplication.util.hasNetworkConnection

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Samira Salah
 */
class HttpInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (hasNetworkConnection()) {
            val request = chain.request().newBuilder().also {
                //  it.addHeader("Authorization",  "token")
                it.addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "*/*")

            }.build()

            return chain.proceed(request)
        } else {
            throw ProjectExceptions.networkException()
        }
    }
}
