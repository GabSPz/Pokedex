package com.example.pokedex.data.model.uiModel

import com.google.android.gms.maps.model.LatLng

data class UiModel(
    val formattedDistance: String,
    val currentLocation: LatLng?,
    val userTrack: List<LatLng>
) {

    companion object {

        val EMPTY = UiModel(
            formattedDistance = "",
            currentLocation = null,
            userTrack = emptyList()
        )
    }
}