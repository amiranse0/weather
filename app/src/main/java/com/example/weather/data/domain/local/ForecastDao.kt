package com.example.weather.data.domain.local

import androidx.room.*
import com.example.weather.data.model.local.LocalForecast
import com.example.weather.data.model.local.ForecastWithHours
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewForecast(localForecast: LocalForecast)

    @Query("DELETE FROM forecast")
    suspend fun deleteForecastTable()

    @Transaction
    @Query("SELECT * FROM forecast")
    suspend fun getForecastWithHours(): List<ForecastWithHours>
}