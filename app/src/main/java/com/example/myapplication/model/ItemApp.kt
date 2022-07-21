package com.example.myapplication.model

import java.io.Serializable

data class ItemApp(
    val adult: Boolean=false,
    val backdrop_path: String="",
    val genre_ids: List<Int>?=null,
    val id: Int=0,

    val overview: String="",

    val poster_path: String="",
    val release_date: String="",
    val title: String="",

    val vote_average: Double=0.0
):Serializable