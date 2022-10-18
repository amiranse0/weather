package com.example.weather

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.weather.util.haveNetwork
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.MutableStateFlow

@HiltAndroidApp
class MainApp : Application(){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
    companion object{
        lateinit var appContext: Context

        fun isActivityVisible(): Boolean {
            return activityVisible
        }

        fun activityResumed() {
            activityVisible = true
        }

        fun activityPaused() {
            activityVisible = false
        }

        private var activityVisible = false

        fun isConnected(): MutableStateFlow<Boolean> = haveNetwork(appContext)
    }
}