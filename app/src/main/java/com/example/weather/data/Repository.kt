package com.example.weather.data

import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.di.RemoteWeatherDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    @RemoteWeatherDataSource
    private val remoteDataSource: IDataSource
) {
    suspend fun getCurrentWeather(query: Map<String,String>): Flow<ResultOf<RemoteWeather>> {
        return flow {
            emit(ResultOf.Loading)
            try {
                val result = remoteDataSource.getCurrentWeather(query)
                emit(ResultOf.Success(result))
            } catch (e:java.lang.Exception){
                emit(ResultOf.Error(e))
            }
        }
    }
}