package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Repository
import com.example.weather.data.ResultOf
import com.example.weather.data.model.Weather
import com.example.weather.data.remote.api.ApiConfigurations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val currentWeatherStateFlow: MutableStateFlow<ResultOf<Weather>> =
        MutableStateFlow(ResultOf.Loading)

    fun getWeather(
        nameOfCity: String = "",
        latitude: Double = 181.0,
        longitude: Double = 181.0,
        queryByCoordinate: Boolean = false
    ): Flow<ResultOf<Weather>> {
        val query: MutableMap<String, String> = mutableMapOf(
            "key" to ApiConfigurations.API_KEY,
            "days" to "7",
            "aqi" to "no",
            "alerts" to "no"
        )

        viewModelScope.launch {
            if (queryByCoordinate) {
                query["q"] = "${latitude},$longitude"
                Log.d("TAG", query.toString())
            } else {
                query["q"] = nameOfCity
            }
            repository.getCurrentWeather(query).collect {
                currentWeatherStateFlow.emit(it)
            }
        }

        return currentWeatherStateFlow
    }

}