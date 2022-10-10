package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weather.data.model.local.Hour

@Dao
interface HourDao {
    @Insert
    suspend fun addNewHour(hour: Hour)

    @Query("DELETE FROM hour")
    suspend fun deleteHourTable()
}