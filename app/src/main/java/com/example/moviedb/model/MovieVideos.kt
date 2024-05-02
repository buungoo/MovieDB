package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideos(
    @SerialName(value = "results")
    var results: List<MovieVideo> = listOf()
)
