package com.example.pokedex.domain

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.R
import com.example.pokedex.data.maps.PermissionsManager
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.data.repository.LocationRepository
import javax.inject.Inject

class ExplorationUseCase @Inject constructor(
    private var locationRepository: LocationRepository,
    private var permissionsManager : PermissionsManager
) {
    val ui = MutableLiveData(UiModel.EMPTY)



    fun onViewCreated(activity: FragmentActivity) {
        locationRepository.getActivity(activity)
        permissionsManager.onCreate(activity)

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
