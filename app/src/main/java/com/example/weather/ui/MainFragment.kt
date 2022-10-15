package com.example.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.R
import com.example.weather.adapters.MainDaysAdapter
import com.example.weather.adapters.MainHoursAdapter
import com.example.weather.data.model.local.Forecast
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.util.ResultOf
import com.example.weather.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val hoursAdapter = MainHoursAdapter()

    private val daysAdapter = MainDaysAdapter()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializingVariables(view)

        binding.hoursList.adapter = hoursAdapter
        binding.daysList.adapter = daysAdapter

        val test = Forecast(
            "amir",
            "1/1/1",
            0.0,
            1.0,
            20.0,
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.facebook.com%2FLiverpoolFC%2F&psig=AOvVaw3tIk6y7jEbqG8JPXA1i6UX&ust=1665905595457000&source=images&cd=vfe&ved=0CA0QjRxqFwoTCOjjo4zc4foCFQAAAAAdAAAAABAD",
            "ok",
            1,
            1,
            1.0,
            1.0,
            11.1,
            1.0,
            1.0,
            "sr",
            1
        )

        val list = listOf(test,test,test,test,test,test,test,test)
        daysAdapter.submitList(list)

        getLatestLocation()

        weatherAndCurrentWeather()

    }

    private fun weatherAndCurrentWeather() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherAndCurrentWeatherStateFlow.collect {
                    when (it) {
                        is ResultOf.Loading -> {

                        }
                        is ResultOf.Error -> {
//                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility =
//                                View.GONE
//                            activity?.findViewById<ConstraintLayout>(R.id.error_view)?.visibility =
//                                View.VISIBLE
                        }
                        is ResultOf.Success -> {
//                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility =
//                                View.GONE
//                            activity?.findViewById<ScrollView>(R.id.result_view)?.visibility =
//                                View.VISIBLE
                            putDataOnViews(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun getLatestLocation() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Toast.makeText(context, "precise permission granted", Toast.LENGTH_LONG)
                            .show()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        Toast.makeText(context, "approximate permission granted", Toast.LENGTH_LONG)
                            .show()
                    }
                    else -> {
                        Toast.makeText(
                            context,
                            "please get permission to this app",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }


        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        viewModel.locationLiveData.postValue(
                            Pair(
                                location.latitude,
                                location.latitude
                            )
                        )
                    }
                    Log.d("LOCATION", location.toString())
                }
        }
    }

    private fun initializingVariables(view: View) {
        binding = FragmentMainBinding.bind(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun putDataOnViews(data: WeatherAndCurrentWeather) {
        binding.locationView.locationTv.text = data.weather.countryName

    }
}