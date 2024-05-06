package com.example.moviedb.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviedb.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "CacheWorker"
class CacheWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
//        return Result.success()

        return withContext(Dispatchers.IO) {
            try {
                val inputDataString = inputData.getString("results")
                val movieResponse = parseMovieResponse(inputDataString)

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