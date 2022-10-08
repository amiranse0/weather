package com.example.weather.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.R
import com.example.weather.data.ResultOf
import com.example.weather.data.model.Weather
import com.example.weather.data.remote.api.ApiConfigurations
import com.example.weather.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        getCurrentWeather()

    }

    private fun getCurrentWeather() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val query: Map<String, String> = mapOf(
                    "q" to "Tehran",
                    "key" to ApiConfigurations.API_KEY,
                    "days" to "7",
                    "aqi" to "no",
                    "alerts" to "no"
                )
                viewModel.getCurrentWeather(query).collect {
                    when (it) {
                        is ResultOf.Loading -> {

                        }
                        is ResultOf.Error -> {
                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
                            activity?.findViewById<ConstraintLayout>(R.id.error_view)?.visibility = View.VISIBLE
                        }
                        is ResultOf.Success -> {
                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
                            activity?.findViewById<ScrollView>(R.id.result_view)?.visibility = View.VISIBLE
                            putDataOnViews(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun putDataOnViews(data: Weather) {
        binding.nameOfCity.text = data.location.name
    }
}