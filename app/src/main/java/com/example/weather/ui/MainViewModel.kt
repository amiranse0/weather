package com.example.weather.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.weather.MainActivity
import com.example.weather.MainApp
import com.example.weather.R
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.data.model.local.ForecastWithHours
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import com.example.weather.util.ResultOf
import com.example.weather.workers.BackgroundRequestWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private val queryMap = mutableMapOf(
        "key" to ApiConfigurations.API_KEY,
        "days" to "7",
        "aqi" to "no",
        "alerts" to "no"
    )

    private fun backgroundUpdatingLocal() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateLocalRequest = PeriodicWorkRequest.Builder(
            BackgroundRequestWorker::class.java,
            3,
            TimeUnit.HOURS
        ).setConstraints(constraints)
            .build()

        val updateLocalWorker = WorkManager.getInstance(MainApp.appContext)
        updateLocalWorker.enqueueUniquePeriodicWork(
            MainActivity.NOTIFICATION_WORKER_ID,
            ExistingPeriodicWorkPolicy.REPLACE,
            updateLocalRequest
        )
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VIEW", "CLEAR")
        backgroundUpdatingLocal()
    }

    init {
        val appContext = MainApp.appContext
        val sharedPref: SharedPreferences = appContext.getSharedPreferences(
            appContext.getString(
                R.string.coordinates
            ), Context.MODE_PRIVATE
        )

        val coordinates =
            sharedPref.getString(appContext.getString(R.string.coordinates), "Tehran") ?: "Toronto"
        getData(coordinates)
    }

    private val _weatherAndCurrentWeatherStateFlow: MutableStateFlow<ResultOf<WeatherAndCurrentWeather>> =
        MutableStateFlow(ResultOf.LoadingEmptyLocal)
    val weatherAndCurrentWeatherStateFlow = _weatherAndCurrentWeatherStateFlow
    private val _weatherWithForecastsStateFlow: MutableStateFlow<ResultOf<List<WeatherWithForecasts>>> =
        MutableStateFlow(ResultOf.LoadingEmptyLocal)
    val weatherWithForecastsStateFlow = _weatherWithForecastsStateFlow
    private val _forecastWithHoursStateFlow: MutableStateFlow<ResultOf<List<ForecastWithHours>>> =
        MutableStateFlow(ResultOf.LoadingEmptyLocal)
    private val forecastWithHoursStateFlow = _forecastWithHoursStateFlow

    private val _dataStateFlow: MutableStateFlow<ResultOf<Triple<WeatherAndCurrentWeather, List<WeatherWithForecasts>, List<ForecastWithHours>>>> =
        MutableStateFlow(ResultOf.LoadingEmptyLocal)
    val dataStatFlow = _dataStateFlow

    private var job: Job? = null

    fun getData(query: String) {
        val scopeQueryMap = queryMap
        scopeQueryMap["q"] = query
        job?.cancel()
        job = viewModelScope.launch {
            weatherRepository.getData(scopeQueryMap).collect {
                _dataStateFlow.emit(it)
            }
        }
    }

}
