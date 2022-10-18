package com.example.weather.data.domain.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weather.data.model.local.LocalHour

@Dao
interface HourDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewHour(localHour: LocalHour)

    @Query("DELETE FROM hour")
    suspend fun deleteHourTable()
}