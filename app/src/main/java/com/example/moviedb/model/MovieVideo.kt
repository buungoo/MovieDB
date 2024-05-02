package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideo(
    @SerialName(value = "name")
    var name: String,
    @SerialName(value = "site")
    var site: String,
    @SerialName(value = "id")
    var id: String
)
