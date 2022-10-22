package com.example.weather.data.domain

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

    suspend fun fetch(query: Map<String, String>) = remoteDataSource.fetch(query)

    suspend fun saveDataToLocal(data: RemoteWeather) {
        localDataSource.updateLocal(data)
    }

    suspend fun isDataExistInLocal() = localDataSource.isDataExistInLocal()

    fun getData(query: Map<String, String>) = flow {
        while (true){
            emit(ResultOf.LoadingEmptyLocal)
            if (localDataSource.isDataExistInLocal()){
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
            }catch (e:Exception){
                if (localDataSource.isDataExistInLocal()) emit(ResultOf.ErrorFIllLocal(e))
                else emit(ResultOf.ErrorEmptyLocal(e))
            }
            kotlinx.coroutines.delay(TIME_STEP)
        }
    }

}