package com.example.moviedb.network

import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import com.example.moviedb.utils.Constants
import com.google.android.gms.common.api.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieDetails

    @GET("{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieReviews

    @GET("{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id")
        movieId: Long,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieVideos
}