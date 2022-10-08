package com.example.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Repository
import com.example.weather.data.ResultOf
import com.example.weather.data.model.current.CurrentWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val currentWeatherStateFlow: MutableStateFlow<ResultOf<CurrentWeather>> = MutableStateFlow(ResultOf.Loading)

    fun getCurrentWeather(query: Map<String, String>): Flow<ResultOf<CurrentWeather>>{
        viewModelScope.launch {
            repository.getCurrentWeather(query).collect{
                currentWeatherStateFlow.emit(it)
            }
        }

        return currentWeatherStateFlow
    }

}