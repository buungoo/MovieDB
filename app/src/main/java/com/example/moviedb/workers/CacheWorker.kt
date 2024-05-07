package com.example.moviedb.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviedb.MovieDBApplication
import com.example.moviedb.R
import com.example.moviedb.database.DefaultAppContainer
import com.example.moviedb.database.MovieDao
import com.example.moviedb.database.MovieDatabase
import com.example.moviedb.model.Movie
import com.example.moviedb.network.MovieDBApiService
import com.google.firebase.functions.dagger.assisted.Assisted
import com.google.firebase.functions.dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "CacheWorker"
class CacheWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    private val applicationContext = ctx.applicationContext as MovieDBApplication

    override suspend fun doWork(): Result {
        val appContainer = applicationContext.container

        return try {
            val movieList = appContainer.moviesRepository.latestFetch
            appContainer.localMoviesRepository.decacheMovies()
            movieList.forEach { fetchedMovie ->
                fetchedMovie.cached = true
                appContainer.localMoviesRepository.insertMovie(fetchedMovie)
            }

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(
                TAG,
                applicationContext.resources.getString(R.string.error_caching_movies),
                throwable
            )
            Result.failure()
        }
    }
}