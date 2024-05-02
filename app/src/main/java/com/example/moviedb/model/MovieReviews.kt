package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviews(
    @SerialName(value = "results")
    var results: List<MovieReview> = listOf()
)
