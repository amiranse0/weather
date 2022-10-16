package com.example.weather.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.*
import com.example.weather.MainActivity
import com.example.weather.workers.NotificationWorker
import java.util.concurrent.TimeUnit


class AlarmNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val REQUEST_CODE = 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()

        val notificationRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()

        val notificationWorker = WorkManager.getInstance(context!!)
        notificationWorker.enqueueUniquePeriodicWork(
            MainActivity.NOTIFICATION_WORKER_ID,
            ExistingPeriodicWorkPolicy.REPLACE,
            notificationRequest
        )

        Log.d("ALARM", "ALARM")
    }

    fun setAlarm(time: Long, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
}