package com.example.myapplication.data.remote.response



/**
 * Created by Samira Salah
 */
class Response<T>(
    val page:Int,
    val results:ArrayList<T>,
    val total_pages:Int,
    val total_results:Int
)
