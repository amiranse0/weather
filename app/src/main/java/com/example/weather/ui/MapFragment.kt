package com.example.weather.ui

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.carto.styles.MarkerStyle
import com.example.weather.R
import com.example.weather.databinding.FragmentMapBinding
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.style.NeshanMapStyle

class MapFragment : Fragment(R.layout.fragment_map) {

    private lateinit var binding: FragmentMapBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapBinding.bind(view)

        mapCustomizations()
    }

    private fun mapCustomizations() {
        binding.mapView.mapStyle = NeshanMapStyle.NESHAN_NIGHT
        binding.mapView.myLocationEnabled = true
    }
}