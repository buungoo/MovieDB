package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReview(
    @SerialName(value = "author")
    var author: String,

//    @SerialName(value = "rating")
//    var rating: Float,

    @SerialName(value = "content")
    var content: String
)
