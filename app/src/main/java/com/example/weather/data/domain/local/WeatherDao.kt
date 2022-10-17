package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.weather.data.model.local.Weather
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather LIMIT 1")
    fun getWeather(): Flow<Weather>

    @Transaction
    @Query("SELECT * FROM weather LIMIT 1")
    fun getWeatherAndCurrentWeather():Flow<WeatherAndCurrentWeather>

    @Transaction
    @Query("SELECT * FROM weather")
    fun getWeatherWithForecasts():Flow<List<WeatherWithForecasts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewWeather(weather: Weather)

    @Query("DELETE FROM weather")
    fun deleteWeatherTable()

    @Query("SELECT COUNT(*) FROM weather")
    fun getNumberOfRecordsOfWeather():Flow<Int>
}