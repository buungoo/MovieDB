package com.example.moviedb.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviedb.database.MovieDao
import com.example.moviedb.database.MovieDatabase
import com.example.moviedb.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

private const val TAG = "CacheWorker"
class CacheWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    private val movieDao: MovieDao = MovieDatabase.getDatabase(ctx).movieDao()

    override suspend fun doWork(): Result = coroutineScope {

        val movies = inputData.getKeyValueArray("movies")

//        if (movies != null) {
//            for (movie in movies ) {
//                Movie(
//                   movie.id,
//                    movie.title,
//                    movie.posterPath,
//                    movie.releaseDate,
//                    movie.overview
//                )
//            }
//        }
        // Convert Data back to Movie objects and insert into Room database
        //val movieList = movies?.map { convertDataToMovie(it) }

        if (movies != null) {
            for (movie in movies) {
                movieDao.insertCacheMovie(movie)
            }
        }

        return@coroutineScope Result.success()
    }
}