package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//data class Movie(
//    var id: Long = 0L,
//    var title: String,
//    var posterPath: String,
//    var backdropPath: String,
//    var releaseDate: String,
//    var overview: String
//)

@Serializable
data class Movie(
    @SerialName(value = "id")
    var id: Long = 0L,

    @SerialName(value = "title")
    var title: String,

    @SerialName(value = "poster_path")
    var posterPath: String,

    @SerialName(value = "release_date")
    var releaseDate: String,

    @SerialName(value = "overview")
    var overview: String,
)