package com.example.weather.data.domain

import androidx.room.withTransaction
import com.example.weather.data.domain.local.AppDatabase
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.util.Mapper
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val database: AppDatabase
) {
    private val weatherDao = database.weatherDao()
    private val currentWeatherDao = database.currentWeatherDao()
    private val forecastDao = database.forecastDao()
    private val hourDao = database.hourDao()

    suspend fun getWeatherAndCurrentWeather() = weatherDao.getWeatherAndCurrentWeather()

    suspend fun getWeatherWithForecasts() = weatherDao.getWeatherWithForecasts()

    suspend fun getForecastWithHours() = forecastDao.getForecastWithHours()

    suspend fun isDataExistInLocal() = weatherDao.getNumberOfRecordsOfWeather() != 0

    suspend fun updateLocal(remoteWeather: RemoteWeather){
        database.withTransaction {
            weatherDao.deleteWeatherTable()
            currentWeatherDao.deleteCurrentWeatherTable()
            forecastDao.deleteForecastTable()
            hourDao.deleteHourTable()

            weatherDao.addNewWeather(Mapper.weatherRemoteToLocal(remoteWeather))
            currentWeatherDao.addNewCurrentWeather(
                Mapper.currentWeatherRemoteToLocal(
                    remoteWeather
                )
            )
            for ((counterDay, item) in remoteWeather.remoteForecast.remoteForecastDays.withIndex()) {
                forecastDao.addNewForecast(
                    Mapper.forecastRemoteToLocal(
                        remoteWeather,
                        counterDay
                    )
                )
                for ((counterHour, itemHour) in item.remoteHour.withIndex()) {
                    hourDao.addNewHour(
                        Mapper.hourRemoteToLocal(
                            remoteWeather,
                            counterHour,
                            counterDay
                        )
                    )
                }
            }
        }
    }
}