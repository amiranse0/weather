package com.example.weather.data.domain

import android.os.CountDownTimer
import android.util.Log
import androidx.room.withTransaction
import com.example.weather.data.domain.local.*
import com.example.weather.data.domain.remote.api.WeatherService
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.util.ResultOf
import com.example.weather.util.boundResource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val database: AppDatabase
) {
    private val weatherDao = database.weatherDao()
    private val currentWeatherDao = database.currentWeatherDao()
    private val forecastDao = database.forecastDao()
    private val hourDao = database.hourDao()

    fun getWeathers(query: Map<String, String>) =
        boundResource(
            query1 = {
                weatherDao.getWeatherAndCurrentWeather()
            },
            query2 = {
                weatherDao.getWeatherWithForecasts()
            },
            query3 = {
                forecastDao.getForecastWithHours()
            },
            fetch = {
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