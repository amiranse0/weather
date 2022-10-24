package com.example.weather.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.util.ResultOf
import com.example.weather.util.getCoordinate
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@HiltWorker
class BackgroundRequestWorker@AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val repository: WeatherRepository
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return coroutineScope {
            val queryMap = mutableMapOf(
                "key" to ApiConfigurations.API_KEY,
                "days" to "7",
                "aqi" to "no",
                "alerts" to "no",
                "q" to getCoordinate(context)
            )

            var result = Result.retry()

            val job = launch(Dispatchers.IO) {
                repository.getData(queryMap).collect {
                    result = when (it) {
                        is ResultOf.Success -> {
                            Log.d("BACK", "Success")
                            Result.success()
                        }
                        is ResultOf.LoadingEmptyLocal, ResultOf.LoadingFillLocal -> {
                            Log.d("BACK", "Retry")
                            Result.retry()
                        }
                        else -> {
                            Log.d("BACK", "Failed")
                            Result.failure()
                        }
                    }
                }
            }

            job.start()

            result
        }
    }
}