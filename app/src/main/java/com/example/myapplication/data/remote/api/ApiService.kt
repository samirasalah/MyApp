package com.example.myapplication.data.remote.api

import com.example.myapplication.data.remote.response.Response
import com.example.myapplication.model.ItemApp
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
/**
 * Created by Samira Salah
 */
interface ApiService {

    @GET("3/search/movie")
    suspend fun getData(
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("query") query:Int,
        @Query("page") page:Int
    ): Response<ItemApp?>
}