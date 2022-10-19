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
import androidx.appcompat.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val sharedPref: SharedPreferences? =
        activity?.getPreferences(Context.MODE_PRIVATE)

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val hoursAdapter = MainHoursAdapter()

    private val daysAdapter = MainDaysAdapter()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializingVariables(view)


        val appBar: Toolbar? = (activity as? AppCompatActivity)?.findViewById(R.id.my_toolbar)
        Log.d("MENU", appBar.toString())
        (activity as? AppCompatActivity)?.setSupportActionBar(appBar);
        val actionBar: ActionBar? = (activity as? AppCompatActivity)?.supportActionBar

        appBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_notification -> {
                    Toast.makeText(context, "notif", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.action_go_to_map -> {
                    Toast.makeText(context, "map", Toast.LENGTH_LONG).show()
                    true
                }
                else -> {
                    false
                }
            }
        }

        val test = LocalForecast(
            1,
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
            1
        )

        val list = listOf(test, test, test, test, test, test, test, test)
        daysAdapter.submitList(list)

        getLatestLocation()

        weatherAndCurrentWeather()
    }

    private fun handleUiState(state: ResultOf<*>) {
        val progressBar = activity?.findViewById<ProgressBar>(R.id.progress_bar)
        val errorLayout = activity?.findViewById<ConstraintLayout>(R.id.error_view)
        val resultView = activity?.findViewById<ScrollView>(R.id.result_view)
        when (state) {
            is ResultOf.Error -> {
                errorLayout?.visibility = View.VISIBLE
                progressBar?.visibility = View.INVISIBLE
                resultView?.visibility = View.INVISIBLE
            }
            is ResultOf.Loading -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.VISIBLE
                resultView?.visibility = View.INVISIBLE
            }
            is ResultOf.Success -> {
                errorLayout?.visibility = View.INVISIBLE
                progressBar?.visibility = View.INVISIBLE
                resultView?.visibility = View.VISIBLE
            }
        }
    }

    private fun weatherAndCurrentWeather() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataStatFlow.collect {
                    handleUiState(it)
                    when (it) {
                        is ResultOf.Loading -> {
                            Log.d("TAG", "Loading")
                        }
                        is ResultOf.Error -> {
                            Log.d("TAG", "Error")
                        }
                        is ResultOf.Success -> {

                            putDataOnViews(it.data)
                            Log.d("TAG", "Success")
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
                    val coordinates: String
                    Log.d("LOCATION", location.toString())
                    if (location != null) {
                        coordinates = "${location.latitude}, ${location.longitude}"
                        with(sharedPref?.edit()) {
                            this?.putString(getString(R.string.coordinates), coordinates)
                            this?.apply()
                        }
                    } else {
                        coordinates =
                            sharedPref?.getString(getString(R.string.coordinates), "Tehran")
                                ?: "Toronto"
                    }
                    viewModel.getData(coordinates)
                }
        }
    }

    private fun initializingVariables(view: View) {
        binding = FragmentMainBinding.bind(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.hoursList.adapter = hoursAdapter
        binding.daysList.adapter = daysAdapter
    }

    private fun putDataOnViews(data: Triple<WeatherAndCurrentWeather, List<WeatherWithForecasts>, List<ForecastWithHours>>) {
        locationUi(data.first.localWeather)
        generalVariablesUi(data.first.localCurrentWeather)

    }

    private fun generalVariablesUi(localCurrentWeather: LocalCurrentWeather) {
        localCurrentWeather.apply {
            binding.todayView.
        }
    }

    private fun locationUi(localWeather: LocalWeather){
        localWeather.apply {
            binding.locationView.locationTv.text =
                context?.getString(R.string.title_name_template, countryName, region, name)
        }
    }
}