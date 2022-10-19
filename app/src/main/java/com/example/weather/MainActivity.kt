package com.example.weather

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.databinding.DialogNotificationCustomizationBinding
import com.example.weather.receivers.AlarmNotificationReceiver
import com.example.weather.ui.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializingVariables()

        getLatestLocation()

        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)

    }

    private fun initializingVariables() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_go_to_map -> {
                findNavController(R.id.fragment).navigate(R.id.action_global_to_mapFragment)
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
        val notificationCustomizationDialog =
            Dialog(this, androidx.transition.R.style.Base_ThemeOverlay_AppCompat)
        dialogBinding = DialogNotificationCustomizationBinding.inflate(layoutInflater)
        notificationCustomizationDialog.setContentView(dialogBinding.root)

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
        val hour: Int = dialogBinding.notificationTimePicker.hour
        val minute: Int = dialogBinding.notificationTimePicker.minute
        val calendar = Calendar.getInstance()
        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH)
        val currentDay: Int = calendar.get(Calendar.DATE)
        calendar.set(
            currentYear,
            currentMonth,
            currentDay,
            hour,
            minute,
            1
        )
        Log.d("ALARM", "${calendar.time}")

        return calendar.timeInMillis
    }

    private fun notificationService() {


    }

    private fun getLatestLocation() {

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Toast.makeText(this, "precise permission granted", Toast.LENGTH_LONG)
                            .show()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        Toast.makeText(this, "approximate permission granted", Toast.LENGTH_LONG)
                            .show()
                    }
                    else -> {
                        Toast.makeText(
                            this,
                            "please get permission to this app",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        } else {
            val sharedPref: SharedPreferences =
                this.getPreferences(Context.MODE_PRIVATE)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    Log.d("LOCATION", location.toString())
                    if (location != null) {
                        val coordinates = "${location.latitude}, ${location.longitude}"
                        with(sharedPref.edit()) {
                            this.putString(
                                getString(R.string.coordinates),
                                coordinates
                            )
                            this.apply()
                        }
                    }
                }
        }
    }
}