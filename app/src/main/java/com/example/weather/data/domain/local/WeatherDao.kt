package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.weather.data.model.local.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeather(): Flow<Weather>

    @Transaction
    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeatherAndCurrentWeather():Flow<WeatherAndCurrentWeather>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getWeatherWithForecasts():Flow<List<WeatherWithForecasts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewWeather(weather: Weather)

    @Query("DELETE FROM weather")
    suspend fun deleteWeatherTable()
}