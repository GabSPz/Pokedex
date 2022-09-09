package com.example.pokedex.data.repository

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.ktx.utils.heatmaps.toWeightedLatLng
import javax.inject.Inject
import kotlin.math.roundToInt

class LocationRepository (private val activity:Fragment)  {


    private val client by lazy {LocationServices.getFusedLocationProviderClient(activity.requireActivity())}

    private val locations = mutableListOf<LatLng>()
    private var distance = 0

    val liveLocation = MutableLiveData<LatLng>()
    val liveLocations = MutableLiveData<List<LatLng>>()
    val liveDistance = MutableLiveData<Int>()

    @SuppressLint("MissingPermission")
    fun getUserLocation(){
         client.lastLocation.addOnSuccessListener { location ->
             val latLng = LatLng(location.latitude, location.longitude)
             locations.add(latLng)
             liveLocation.postValue(latLng)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            val currentLocation = result.lastLocation
            val latLng = LatLng(currentLocation!!.latitude, currentLocation.longitude)

            val lastLocation = locations.lastOrNull()
            if (lastLocation != null) {
                distance +=
                    SphericalUtil.computeDistanceBetween(lastLocation, latLng).roundToInt()
                liveDistance.value = distance
            }
            locations.add(latLng)
            liveLocations.value = locations
        }
    }

    @SuppressLint("MissingPermission")
    fun trackUser() {

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 4000

        client.requestLocationUpdates(locationRequest, locationCallback,
            Looper.getMainLooper())
    }

    fun stopTracking() {
        client.removeLocationUpdates(locationCallback)
        locations.clear()
        distance = 0
    }
}