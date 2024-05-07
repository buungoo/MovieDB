package com.example.moviedb.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviedb.R
import com.example.moviedb.network.MovieDBApiService
import com.google.firebase.functions.dagger.assisted.Assisted
import com.google.firebase.functions.dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "CacheWorker"
class CacheWorker @AssistedInject constructor(
    @Assisted private val api: MovieDBApiService,
    private val ctx: Context,
    private val params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
//        return Result.success()


        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPopularMovies()

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
}