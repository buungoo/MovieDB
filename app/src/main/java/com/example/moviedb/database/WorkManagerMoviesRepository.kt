package com.example.moviedb.database

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import com.example.moviedb.network.MovieDBApiService
import com.example.moviedb.workers.CacheWorker

class WorkManagerMoviesRepository(context: Context, private val apiService: MovieDBApiService) : MoviesRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun getPopularMovies(): MovieResponse {
        val apiMovies = apiService.getPopularMovies()
        val cacheWorker = OneTimeWorkRequestBuilder<CacheWorker>()
        val movieDataList = apiMovies.results.map { convertMovieToInputDataForWorker(it) }
        val inputData  = workDataOf("movies" to movieDataList)
        cacheWorker.setInputData(inputData)
        workManager.enqueue(cacheWorker.build())
        return apiMovies
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

    fun convertMovieToInputDataForWorker(movie: Movie): Data {
        val movieData = Data.Builder()
            .putLong("id", movie.id)
            .putString("title", movie.title)
            .putString("poster_path", movie.posterPath)
            .putString("release_date", movie.releaseDate)
            .putString("overview", movie.overview)
            .build()
        return movieData
    }

}
