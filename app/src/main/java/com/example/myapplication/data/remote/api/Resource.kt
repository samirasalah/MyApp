package com.example.myapplication.data.remote.api

import okhttp3.ResponseBody

/**
 * Created by Samira Salah
 */
sealed class Resource<out T> {
    data class Success<out T>(val value: T): Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val isIOException: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val message: String?
    ): Resource<Nothing>()

    object Loading: Resource<Nothing>()
}