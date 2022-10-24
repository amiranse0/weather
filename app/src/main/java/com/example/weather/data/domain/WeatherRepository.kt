package com.example.weather.data.domain

import android.util.Log
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.util.ResultOf
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    companion object {
        val TIME_STEP = TimeUnit.MINUTES.toMillis(5)
    }

    private suspend fun fetch(query: Map<String, String>) = remoteDataSource.fetch(query)

    private suspend fun saveDataToLocal(data: RemoteWeather) {
        localDataSource.updateLocal(data)
    }

    fun getData(query: Map<String, String>) = flow {
        while (true) {
            emit(ResultOf.LoadingEmptyLocal)
            if (localDataSource.isDataExistInLocal()) {
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
                emit(ResultOf.LoadingFillLocal)
            }
            try {
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
                if (localDataSource.isDataExistInLocal()) emit(ResultOf.ErrorFIllLocal(e))
                else emit(ResultOf.ErrorEmptyLocal(e))
            }
            kotlinx.coroutines.delay(TIME_STEP)
        }
    }

    fun getDisasters(query: Map<String, String>) = flow {
        emit(ResultOf.LoadingEmptyLocal)
        try {
            val disasters = remoteDataSource.getDisasters(query)
            Log.d("ALARM", "success request")
            emit(ResultOf.Success(disasters))
        } catch (e:Exception){
            emit(ResultOf.ErrorEmptyLocal(e))
            Log.d("ALARM", e.message.toString())
        }
    }
}