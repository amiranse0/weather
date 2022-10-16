package com.example.weather.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weather.R
import com.example.weather.data.domain.WeatherRepository
import javax.inject.Inject

class NotificationWorker @Inject constructor(
    private val repository: WeatherRepository,
    private val context: Context,
    private val workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    companion object{
        const val CHANNEL_ID = "1"
        const val TAG = "DISASTER"
        const val NOTIFICATION_ID = 1
    }

    override suspend fun doWork(): Result {
        notification()
        channelNotification()

        return Result.retry()
    }

    private fun notification(){
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_cloud_queue_24)
            .setContentTitle(context.getString(R.string.weather))
            .setContentText("Something")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

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

//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            notificationManager.createNotificationChannel(channel)
        }
    }
}