package com.example.myapplication.io.repository

import com.example.myapplication.data.di.exception.ProjectExceptions
import com.example.myapplication.data.remote.api.ApiService
import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.viewModel.MainActivityViewModel
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by Samira Salah
 */
abstract class BaseRepository(private val service: ApiService?=null) {
    suspend fun <T> safeApiCall(
            apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is UnknownHostException-> {
                        Resource.Failure(false, true,null, null,throwable.message)
                    }
                    is JsonSyntaxException-> {
                        Resource.Failure(false, true,null, null,throwable.message)
                    }
                    is SocketTimeoutException -> {
                        Resource.Failure(true, false,null, null,throwable.message)
                    }
                    is ProjectExceptions.UnknownHostException -> {
                        Resource.Failure(true, false,null, null,throwable.message)
                    }
                    is IOException -> {
                        Resource.Failure(false, true,null, null,throwable.message)
                    }
                    is HttpException -> {
                        Resource.Failure(false,false, throwable.code(), throwable.response()?.errorBody(),throwable.response()?.errorBody().toString())
                    }
                    else -> {
                        Resource.Failure(true,  false,null,null,throwable.message)
                    }
                }
            }
        }
    }


}