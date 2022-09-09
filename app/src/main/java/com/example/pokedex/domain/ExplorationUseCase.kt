package com.example.pokedex.domain

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.R
import com.example.pokedex.data.maps.PermissionsManager
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.data.repository.LocationRepository
import javax.inject.Inject

class ExplorationUseCase (private val activity: Fragment) {
    private val locationRepository = LocationRepository(activity)
    private val permissionsManager  = PermissionsManager(activity, locationRepository)

    val ui = MutableLiveData(UiModel.EMPTY)
    val totalDistance = MutableLiveData<Int>()


    fun onViewCreated() {
        locationRepository.liveLocations.observe(activity) { locations ->
            val current = ui.value
            ui.value = current?.copy(userTrack = locations)
        }

        locationRepository.liveLocation.observe(activity) { currentLocation ->
            val current = ui.value
            ui.value = current?.copy(currentLocation = currentLocation)
        }

        locationRepository.liveDistance.observe(activity) { distance ->
            val current = ui.value
            val formattedDistance = activity.getString(R.string.distance_value, distance)
            ui.value = current?.copy(formattedDistance = formattedDistance)
            totalDistance.postValue(distance)
        }

    }


    fun onMapLoaded() {
        permissionsManager.requestUserLocation()
    }

    fun startTracking() {
        permissionsManager.requestActivityRecognition()
        locationRepository.trackUser()

        val currentUi = ui.value
         ui.value = currentUi?.copy(
            formattedDistance = UiModel.EMPTY.formattedDistance
        )
    }



    fun stopTracking() {
        locationRepository.stopTracking()
    }
}
