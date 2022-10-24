package com.example.weather.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weather.R
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.data.model.remote.Alert
import com.example.weather.data.model.remote.Alerts
import com.example.weather.util.ResultOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val repository: WeatherRepository
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val CHANNEL_ID = "1"
        const val TAG = "DISASTER"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        return coroutineScope {
            val coordinate: String = getCoordinates()
            val queryMap = mutableMapOf(
                "key" to ApiConfigurations.API_KEY,
                "days" to "7",
                "aqi" to "no",
                "alerts" to "yes",
                "q" to coordinate
            )

            var result = Result.retry()

            val job = launch(Dispatchers.IO) {
                repository.getDisasters(queryMap).collect{
                    result = when(it) {
                        is ResultOf.Success -> {
                            notification(it.data)
                            channelNotification()
                            Log.d("ALARM", "Success")
                            Result.success()
                        }
                        is ResultOf.LoadingEmptyLocal -> {
                            Log.d("ALARM", "Retry")
                            Result.retry()
                        }
                        else -> {
                            Log.d("ALARM", "Failed")
                            Result.failure()
                        }
                    }
                }
            }

            job.start()

            Log.d("ALARM", "Worker before")
            //job.wait()
            Log.d("ALARM", "Worker")
            result
        }

    }

    private fun getCoordinates(): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.coordinates), Context.MODE_PRIVATE)

        return sharedPreferences.getString(context.getString(R.string.coordinates), "Tehran")
                ?: "Toronto"
    }

    private fun notification(alerts: List<Alert>) {
        val builder = NotificationCompat
            .Builder(context, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_baseline_cloud_queue_24)
                if (alerts.isEmpty()){
                    setContentTitle("disasters")
                    setContentText("there is no disaster for your territory")
                } else{
                    setContentTitle(alerts.first().event)
                    setContentText(alerts.first().description)
                }
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun channelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL"
            val descriptionText = "disasters"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}