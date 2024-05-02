package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String
)
