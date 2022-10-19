package com.example.weather.data.domain

import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.util.ResultOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    companion object{
        val TIME_STEP = TimeUnit.MINUTES.toMillis(3)
    }
    suspend fun fetch(query: Map<String, String>) = remoteDataSource.fetch(query)

    suspend fun saveDataToLocal(data: RemoteWeather) {
        localDataSource.updateLocal(data)
    }

    suspend fun isDataExistInLocal() = localDataSource.isDataExistInLocal()

    fun getData(query: Map<String, String>) = flow {
        while (true){
            try {
                emit(ResultOf.Loading)
                val data = fetch(query)
                saveDataToLocal(data)
                val weatherAndCurrentWeather = localDataSource.getWeatherAndCurrentWeather()
                val weatherWithForecasts = localDataSource.getWeatherWithForecasts()
                val forecastsWithHours = localDataSource.getForecastWithHours()
                emit(
                    ResultOf.Success(
                        Triple(
                            weatherAndCurrentWeather,
                            weatherWithForecasts,
                            forecastsWithHours
                        )
                    )
                )
            } catch (e: Exception) {
                emit(ResultOf.Error(e))
            }
            kotlinx.coroutines.delay(TIME_STEP)
        }
    }

}