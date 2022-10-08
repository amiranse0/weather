package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.data.ResultOf
import com.example.weather.data.remote.api.ApiConfigurations
import com.example.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val query: Map<String, String> = mapOf(
                    "q" to "tehran",
                    "appid" to ApiConfigurations.API_KEY
                )
                viewModel.getCurrentWeather(query).collect {
                    when (it) {
                        is ResultOf.Loading -> {
                            binding.text.text = "loading"
                        }
                        is ResultOf.Error -> {
                            binding.text.text = it.exception.toString()
                        }
                        is ResultOf.Success -> {
                            binding.text.text = it.data.toString()
                        }
                    }
                }
            }
        }
    }
}