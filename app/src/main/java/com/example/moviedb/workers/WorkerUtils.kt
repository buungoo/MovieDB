package com.example.moviedb.workers

import androidx.work.Data
import com.example.moviedb.model.CacheMovie
import com.example.moviedb.model.Movie
import kotlinx.serialization.json.Json

fun convertDataToMovie(data: Data): Movie {
    val id = data.getLong("id", 0L)
    val title = data.getString("title") ?: ""
    val posterPath = data.getString("poster_path") ?: ""
    val releaseDate = data.getString("release_date") ?: ""
    val overview = data.getString("overview") ?: ""

    return Movie(id, title, posterPath, releaseDate, overview)
}

fun Data.getKeyValueArray(key: String): List<Movie>? {
    val jsonString = this.getString(key)
    return if (!jsonString.isNullOrEmpty()) {
        try {
            Json.decodeFromString<List<Movie>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    } else {
        null
    }
}