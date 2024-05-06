package com.example.moviedb.database

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.moviedb.model.Movie
import com.example.moviedb.model.MovieDetails
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.MovieReviews
import com.example.moviedb.model.MovieVideos
import com.example.moviedb.network.MovieDBApiService
import com.example.moviedb.workers.CacheWorker
import java.io.Serializable

class WorkManagerMoviesRepository(context: Context, private val movieDao: MovieDao, private val apiService: MovieDBApiService) : MoviesRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun getPopularMovies(): MovieResponse {
        val apiMovies = apiService.getPopularMovies()
        val cacheWorker = OneTimeWorkRequestBuilder<CacheWorker>()
        cacheWorker.setInputData(createInputDataForWorkRequest(apiMovies))

        workManager.enqueue(cacheWorker.build())
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

    suspend fun insertMovies(movieList: List<Movie>) {
//        movieDao.insertFavoriteMovie(movie)
//        insert
    }

    private fun createInputDataForWorkRequest(movieResponse: MovieResponse): Data {
        val inputData = Data.Builder()
            .put("results", movieResponse)
            .build()
        return inputData
    }
}
