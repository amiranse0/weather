package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.weather.data.model.local.LocalWeather
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather LIMIT 1")
    fun getWeather(): Flow<LocalWeather>

    @Transaction
    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeatherAndCurrentWeather(): WeatherAndCurrentWeather

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getWeatherAndCurrentWeatherForWidget(): List<WeatherAndCurrentWeather>

    @Transaction
    @Query("SELECT * FROM weather")
    suspend fun getWeatherWithForecasts(): List<WeatherWithForecasts>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewWeather(localWeather: LocalWeather)

    @Query("DELETE FROM weather")
    fun deleteWeatherTable()

    @Query("SELECT COUNT(*) FROM weather")
    suspend fun getNumberOfRecordsOfWeather(): Int
}