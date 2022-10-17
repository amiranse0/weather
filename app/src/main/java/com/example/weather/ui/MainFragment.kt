package com.example.weather.ui

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.adapters.MainDaysAdapter
import com.example.weather.adapters.MainHoursAdapter
import com.example.weather.data.model.local.Forecast
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.util.ResultOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val sharedPref: SharedPreferences? =
        activity?.getPreferences(Context.MODE_PRIVATE)

    private val requestPermissionLauncher =
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

        val list = listOf(test, test, test, test, test, test, test, test)
        daysAdapter.submitList(list)

        getLatestLocation()

        weatherAndCurrentWeather()

        appBarOptionsSelection()

    }

    private fun appBarOptionsSelection() {
        val mainActivity = activity as AppCompatActivity?

        val toolbar: Toolbar? = mainActivity?.supportActionBar?.customView as Toolbar?

        Log.d("TAG", "$toolbar")
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_notification -> {
                    true
                }
                R.id.action_go_to_map -> {
                    findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
                    Toast.makeText(requireContext(), "TEST", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun weatherAndCurrentWeather() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherAndCurrentWeatherStateFlow.collect {
                    when (it) {
                        is ResultOf.Loading -> {
                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility =
                                View.VISIBLE
                            activity?.findViewById<ConstraintLayout>(R.id.error_view)?.visibility =
                                View.GONE
                            activity?.findViewById<ScrollView>(R.id.result_view)?.visibility =
                                View.INVISIBLE
                        }
                        is ResultOf.Error -> {
                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility =
                                View.GONE
                            activity?.findViewById<ConstraintLayout>(R.id.error_view)?.visibility =
                                View.VISIBLE
                        }
                        is ResultOf.Success -> {
                            activity?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility =
                                View.GONE
                            activity?.findViewById<ScrollView>(R.id.result_view)?.visibility =
                                View.VISIBLE
                            putDataOnViews(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun getLatestLocation() {

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
                    val coordinates: String
                    if (location != null) {
                        coordinates = "${location.latitude}, ${location.longitude}"
                        with(sharedPref?.edit()) {
                            this?.putString(getString(R.string.coordinates), coordinates)
                            this?.apply()
                        }
                    } else {
                        coordinates =
                            sharedPref?.getString(getString(R.string.coordinates), "Tehran")
                                ?: "Tehran"
                    }
                    viewModel.getWeathers(coordinates)
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