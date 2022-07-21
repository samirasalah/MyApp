package com.example.myapplication.io.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.api.ApiService
import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.data.remote.response.Response
import com.example.myapplication.model.ItemApp
import com.google.gson.JsonObject
import java.util.*



/**
 * Created by Samira Salah
 */
class UserRepository(private val apiService: ApiService):BaseRepository(apiService){
   var query=1

    suspend fun getData(page:Int):Resource<Response<ItemApp?>> = safeApiCall {

        apiService.getData(BuildConfig.ApiKey,Locale.getDefault().language,query,page)
    }
}