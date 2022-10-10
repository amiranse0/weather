package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.model.local.CurrentWeather

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCurrentWeather(currentWeather: CurrentWeather)

    @Query("DELETE FROM current")
    suspend fun deleteCurrentWeatherTable()
}