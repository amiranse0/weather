package com.example.weather

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.databinding.DialogNotificationCustomizationBinding
import com.example.weather.receivers.AlarmNotificationReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        MainApp.activityPaused()
    }

    override fun onResume() {
        super.onResume()
        MainApp.activityResumed()
    }

    companion object {
        const val NOTIFICATION_WORKER_ID = "ID"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: DialogNotificationCustomizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("ResourceType")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_go_to_map -> {
                findNavController(R.id.fragment).navigate(R.id.action_mainFragment_to_mapFragment)
                true
            }
            R.id.action_notification -> {
                setNotifications()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setNotifications() {
        dialogBinding = DialogNotificationCustomizationBinding.inflate(layoutInflater)
        val notificationCustomizationDialog = Dialog(this, androidx.transition.R.style.Base_ThemeOverlay_AppCompat)
        notificationCustomizationDialog.also {
            setContentView(dialogBinding.root)
        }

        dialogBinding.submitButton.setOnClickListener {
            val time = getTime()
            val alarmNotificationReceiver = AlarmNotificationReceiver()
            alarmNotificationReceiver.setAlarm(time, this)
            notificationCustomizationDialog.dismiss()
        }

        dialogBinding.dismissButton.setOnClickListener {
            notificationCustomizationDialog.dismiss()
        }
        notificationCustomizationDialog.show()

        notificationService()
    }

    private fun getTime(): Long {
        val hour = dialogBinding.notificationTimePicker.hour
        val minute = dialogBinding.notificationTimePicker.minute
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DATE)
        calendar.set(
            currentYear,
            currentMonth + 1,
            currentDay,
            hour,
            minute,
            0
        )
        Log.d("TAG", "$currentDay, $currentYear, $currentMonth")
        return calendar.timeInMillis
    }

    private fun notificationService() {


    }
}