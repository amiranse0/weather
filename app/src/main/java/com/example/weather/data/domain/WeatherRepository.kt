package com.example.weather.data.domain

import com.example.weather.data.model.remote.RemoteWeather
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {


    suspend fun fetch(query: Map<String, String>) = remoteDataSource.fetch(query)

    suspend fun saveDataToLocal(data: RemoteWeather){
        localDataSource.updateLocal(data)
    }

    suspend fun isDataExistInLocal() = localDataSource.isDataExistInLocal()

    fun getWeatherAndCurrentWeather() = localDataSource.getWeatherAndCurrentWeather()
    fun getWeatherWithForecasts() = localDataSource.getWeatherWithForecasts()
    fun getForecastWithHours() = localDataSource.getForecastWithHours()

    //TODO("fetch date from remote")

}