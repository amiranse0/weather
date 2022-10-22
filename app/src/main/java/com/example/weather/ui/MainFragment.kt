package com.example.weather.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.R
import com.example.weather.adapters.MainDaysAdapter
import com.example.weather.adapters.MainHoursAdapter
import com.example.weather.data.model.local.*
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.util.ResultOf
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val hoursAdapter = MainHoursAdapter()

    private val daysAdapter = MainDaysAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializingVariables(view)

        primaryRequest()

        getWeathers()

        searchWeather()
    }

    private fun searchWeather() {
        binding.nameQueryEd.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getData(binding.nameQueryEd.text.toString())

                val manager = requireActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as InputMethodManager?
                if (manager != null) {
                    manager
                        .hideSoftInputFromWindow(
                            v?.windowToken, 0
                        )
                }

                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun primaryRequest(){
        val sharedPref: SharedPreferences =
            requireActivity().getSharedPreferences(getString(R.string.coordinates), Context.MODE_PRIVATE)

        val coordinates =
            sharedPref.getString(getString(R.string.coordinates), "Tehran")
                ?: "Toronto"

        viewModel.getData(coordinates)
    }

    private fun handleUiState(state: ResultOf<*>) {
        val progressBar = activity?.findViewById<ProgressBar>(R.id.progress_bar)
        val errorLayout = activity?.findViewById<ConstraintLayout>(R.id.error_view)
        val resultView = activity?.findViewById<ScrollView>(R.id.result_view)
        when (state) {
            is ResultOf.ErrorEmptyLocal -> {
                errorLayout?.visibility = View.VISIBLE
                progressBar?.visibility = View.INVISIBLE
                resultView?.visibility = View.INVISIBLE
            }
            is ResultOf.LoadingEmptyLocal -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.VISIBLE
                resultView?.visibility = View.INVISIBLE
            }
            is ResultOf.Success -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.INVISIBLE
                resultView?.visibility = View.VISIBLE
            }
            is ResultOf.LoadingFillLocal -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.VISIBLE
                resultView?.visibility = View.VISIBLE
            }
            is ResultOf.ErrorFIllLocal -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.INVISIBLE
                resultView?.visibility = View.VISIBLE
                Toast.makeText(requireContext(), getString(R.string.something_is_wrong), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeathers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataStatFlow.collect {
                    handleUiState(it)
                    when (it) {
                        is ResultOf.LoadingEmptyLocal -> {
                            Log.d("TAG", "Loading empty")
                        }
                        is ResultOf.ErrorEmptyLocal -> {
                            Log.d("TAG", "Error empty")
                        }
                        is ResultOf.Success -> {
                            putDataOnViews(it.data)
                            Log.d("TAG", "Success")
                        }
                        is ResultOf.LoadingFillLocal -> Log.d("TAG", "Loading fill")
                        is ResultOf.ErrorFIllLocal -> Log.d("TAG", "Error fill")
                    }
                }
            }
        }
    }

    private fun initializingVariables(view: View) {
        binding = FragmentMainBinding.bind(view)
        binding.hoursList.adapter = hoursAdapter
        binding.daysList.adapter = daysAdapter
    }

    private fun putDataOnViews(data: Triple<WeatherAndCurrentWeather, List<WeatherWithForecasts>, List<ForecastWithHours>>) {
        binding.localWeather = data.first.localWeather
        binding.localCurrentWeather = data.first.localCurrentWeather
        binding.todayWeather = data.second.first().localForecasts.first()

        val todayHoursList:List<LocalHour> = data.third.first().localHours
        hoursAdapter.submitList(todayHoursList)

        val daysList:List<LocalForecast> = data.second.first().localForecasts
        daysAdapter.submitList(daysList)
    }

}