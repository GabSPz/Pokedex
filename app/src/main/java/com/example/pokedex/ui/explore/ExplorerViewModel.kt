package com.example.pokedex.ui.explore

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.domain.ExplorationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class ExplorerViewModel ( private val activity: Fragment ) : ViewModel() {
    private val explorationUseCase =  ExplorationUseCase(activity)

    val ui = MutableLiveData<UiModel>()
    val distance = MutableLiveData<Int>()

    fun onCreate(){
            explorationUseCase.onViewCreated( )
            explorationUseCase.ui.observe(activity, Observer {
                ui.postValue(it)
            })
    }

    fun onMapLoaded(){
        explorationUseCase.onMapLoaded()
    }

    fun onStopExplore(){
        explorationUseCase.stopTracking()
    }

    fun onStartExplore(){
        explorationUseCase.startTracking()
    }

    fun getDistance(){
        explorationUseCase.totalDistance.observe(activity, Observer {
            distance.postValue(it)
        })
    }

    fun resetDistance(){
        distance.postValue(0)
    }

}