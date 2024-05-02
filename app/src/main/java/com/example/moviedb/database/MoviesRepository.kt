package com.example.moviedb.database

import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import com.example.moviedb.network.MovieDBApiService


interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse

    suspend fun getTopRatedMovies(): MovieResponse

    suspend fun getMovieDetails(movieId: Long): MovieDetails

    suspend fun getMovieReviews(movieId: Long): MovieReviews

    suspend fun getMovieVideos(movieId: Long): MovieVideos
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun getMovieReviews(movieId: Long): MovieReviews {
        return apiService.getMovieReviews(movieId)
    }

    override suspend fun getMovieVideos(movieId: Long): MovieVideos {
        return apiService.getMovieVideos(movieId)
    }
}