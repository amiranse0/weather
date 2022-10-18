package com.example.weather.data.domain

import androidx.room.withTransaction
import com.example.weather.data.domain.local.*
import com.example.weather.data.domain.remote.api.WeatherService
import com.example.weather.data.model.local.ForecastWithHours
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import com.example.weather.data.model.remote.Alert
import com.example.weather.util.ResultOf
import com.example.weather.util.boundResource
import com.example.weather.util.fetchAndSaveDataToLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val database: AppDatabase
) {
    private val weatherDao = database.weatherDao()
    private val currentWeatherDao = database.currentWeatherDao()
    private val forecastDao = database.forecastDao()
    private val hourDao = database.hourDao()

//    suspend fun getWeathers1(query: Map<String, String>): Triple<MutableStateFlow<ResultOf<WeatherAndCurrentWeather>>, MutableStateFlow<ResultOf<WeatherWithForecasts>>, MutableStateFlow<ResultOf<ForecastWithHours>>> {
//        requestToServerAndSaveData(query, 5000)
//    }

    suspend fun requestToServerAndSaveData(query: Map<String, String>, timeStep: Long) {
        fetchAndSaveDataToLocal(
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
            },
            timeStep = timeStep
        )
    }

    suspend fun getWeathers(query: Map<String, String>) =
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
            query4 = {
                weatherDao.getNumberOfRecordsOfWeather()
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

    suspend fun getAlerts(query: Map<String, String>): Flow<ResultOf<List<Alert>>> =
        flow {
            emit(ResultOf.Loading)
            try {
                val data = weatherService.getWeather(query)
                emit(ResultOf.Success(data.alerts))
            } catch (e: Exception) {
                emit(ResultOf.Error(e))
            }
        }

}