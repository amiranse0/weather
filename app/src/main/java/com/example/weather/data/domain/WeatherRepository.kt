package com.example.weather.data.domain

import android.util.Log
import androidx.room.withTransaction
import com.example.weather.data.domain.local.*
import com.example.weather.data.domain.remote.api.WeatherService
import com.example.weather.data.model.local.Weather
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.util.ResultOf
import com.example.weather.util.boundResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val database: AppDatabase
) {
    companion object {
        const val DELAY_TIME_UPDATE_LOCAL = 5000L//TimeUnit.MINUTES.toMillis(3)
    }

    private val weatherDao = database.weatherDao()
    private val currentWeatherDao = database.currentWeatherDao()
    private val forecastDao = database.forecastDao()
    private val hourDao = database.hourDao()

    fun getWeatherAndCurrentWeather(query: Map<String, String>): Flow<ResultOf<WeatherAndCurrentWeather>> =
        boundResource(
            query = {
                weatherDao.getWeatherAndCurrentWeather()
            },
            fetch = {
                delay(DELAY_TIME_UPDATE_LOCAL)
                Log.d("TAG", "request")
                weatherService.getWeather(query)
            },
            saveFetchResult = {
                database.withTransaction {
                    weatherDao.deleteWeatherTable()
                    currentWeatherDao.deleteCurrentWeatherTable()
                    forecastDao.deleteForecastTable()
                    hourDao.deleteHourTable()

                    weatherDao.addNewWeather(Mapper.weatherRemoteToLocal(it))
                    currentWeatherDao.addNewCurrentWeather(
                        Mapper.currentWeatherRemoteToLocal(
                            it
                        )
                    )
                    for ((counterDay, item) in it.remoteForecast.remoteForecastDays.withIndex()) {
                        forecastDao.addNewForecast(
                            Mapper.forecastRemoteToLocal(
                                it,
                                counterDay
                            )
                        )
                        for ((counterHour, itemHour) in item.remoteHour.withIndex()) {
                            hourDao.addNewHour(
                                Mapper.hourRemoteToLocal(
                                    it,
                                    counterHour,
                                    counterDay
                                )
                            )
                        }
                    }
                }
            }
        )

}