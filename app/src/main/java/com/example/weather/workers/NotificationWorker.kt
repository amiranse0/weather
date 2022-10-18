package com.example.weather.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weather.R
import com.example.weather.data.domain.WeatherRepository
import javax.inject.Inject

class NotificationWorker @Inject constructor(
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

        Log.d("ALARM", "Worker")

        return Result.success()
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