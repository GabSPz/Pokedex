package com.example.pokedex.ui.explore

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.domain.ExplorationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExplorerViewModel @Inject constructor(
private val explorationUseCase: ExplorationUseCase
) : ViewModel() {
    val ui = MutableLiveData<UiModel>()

    fun onCreate(activity: FragmentActivity){
        explorationUseCase.onViewCreated(activity)
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

}