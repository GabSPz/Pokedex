package com.example.pokedex.data.maps

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.data.repository.LocationRepository
import javax.inject.Inject

class PermissionsManager @Inject constructor(
    private val locationRepository: LocationRepository,
) {
    private val _activity = MutableLiveData<FragmentActivity>()
    private var activity: FragmentActivity? = _activity.value


    fun onCreate(activityFragmentActivity: FragmentActivity){
        _activity.postValue(activityFragmentActivity)
    }

    private val locationPermissionProvider = activity?.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            locationRepository.getUserLocation()
        }
    }

    fun requestUserLocation() {
        locationPermissionProvider?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        locationPermissionProvider?.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private val activityRecognitionPermissionProvider =
        activity?.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
            }
        }

    fun requestActivityRecognition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activityRecognitionPermissionProvider?.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
        }
    }
}