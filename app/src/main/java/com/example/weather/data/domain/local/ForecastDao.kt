package com.example.weather.data.domain.local

import androidx.room.*
import com.example.weather.data.model.local.Forecast
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewForecast(forecast: Forecast)

    @Query("DELETE FROM forecast")
    suspend fun deleteForecastTable()

    @Transaction
    @Query("SELECT * FROM forecast")
    suspend fun getForecastWithHours(): Flow<List<ForecastWithHours>>
}