package com.example.weather.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.weather.MainActivity
import com.example.weather.workers.NotificationWorker
import java.util.concurrent.TimeUnit


class AlarmNotificationReceiver : BroadcastReceiver() {
    companion object {
        const val REQUEST_CODE = 1
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationRequest = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            15,
            TimeUnit.MINUTES
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
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        Log.d("ALARM", "SET")
    }


    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmNotificationReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }
}