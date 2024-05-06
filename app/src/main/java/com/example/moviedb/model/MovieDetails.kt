package com.example.moviedb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    @SerialName(value = "backdrop_path")
    var backdropPath: String = "",

    @SerialName(value = "genres")
    var genres: List<Genre> = listOf(),

    @SerialName(value = "homepage")
    var homepage: String? = null,

    @SerialName(value = "imdb_id")
    var imdbId: String? = null,
)