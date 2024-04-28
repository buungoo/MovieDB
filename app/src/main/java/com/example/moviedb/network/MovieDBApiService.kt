package com.example.moviedb.network

import com.example.moviedb.model.MovieResponse
import com.example.moviedb.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApiService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse
}