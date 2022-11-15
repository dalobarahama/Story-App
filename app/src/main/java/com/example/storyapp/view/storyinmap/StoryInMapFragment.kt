package com.example.storyapp.view.storyinmap

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.storyapp.R
import com.example.storyapp.data.model.Result
import com.example.storyapp.viewmodel.MapViewModel
import com.example.storyapp.viewmodel.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class StoryInMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private val boundsBuilder = LatLngBounds.builder()
    private val viewModel: MapViewModel by viewModels {
        ViewModelFactory()
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = true
    private var lastKnownLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_story_in_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapView = view.findViewById<MapView>(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        val sharedPref = view.context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "123") ?: ""

//        viewModel.getStoryWithLocation(token).observe(viewLifecycleOwner) {
//            addManyMarker(it)
//        }

//        viewModel.getTextSearch("AIzaSyAOEh-SStUpG-Kn4R-WCE1W5T-7geOHcC0",
//            "Indomaret",
//            "500",
//            LatLng(
//                lastKnownLocation!!.latitude, lastKnownLocation!!.longitude
//            )).observe(viewLifecycleOwner) {
//            val list: ArrayList<Result> = emptyList<Result>() as ArrayList<Result>
//            for (i in 0..4) {
//                val result = it.results[i]
//                list.add(result)
//            }
//
//            addManyMarker(list)
//        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        map.isMyLocationEnabled = true

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        getDeviceLocation()
    }

    private fun addManyMarker(result: Result) {
//        results.forEach { result ->
        val latLng = LatLng(result.geometry.location.lat, result.geometry.location.lng)
        map.addMarker(MarkerOptions().position(latLng).title(result.name))
        boundsBuilder.include(latLng)
//        }

        val bounds: LatLngBounds = boundsBuilder.build()
        map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
//                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude),
//                                14.0f
//                            ))
                            viewModel.getTextSearch(
                                "AIzaSyAOEh-SStUpG-Kn4R-WCE1W5T-7geOHcC0",
                                "indomaret",
                                "1500",
                                "${lastKnownLocation!!.latitude}" + "%2C" + "${lastKnownLocation!!.longitude}"
                            )
                                .observe(viewLifecycleOwner) {
                                    for (i in 0..4) {
                                        Log.d("StoryInMapFragment", "mapResponse: $it")
                                        val result = it.results[i]
                                        addManyMarker(result)
                                    }
//                                    Log.d("StoryInMapFragment", "mapResponse: ${it.results}")
//                                    addManyMarker(it.results)
                                }
                        }
                    }
                }
            } else {
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(-6.183102834926161, 106.83094375755323), 14.0f
                    )
                )
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e);
        }
    }
}
















