package com.example.pokedex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.domain.GetPokedexUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokedexUseCase: GetPokedexUseCase
): ViewModel() {

    private val _pokedexList = MutableLiveData<List<PokedexModel>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val pokedexList: LiveData<List<PokedexModel>> = _pokedexList
    val isLoading: LiveData<Boolean> = _isLoading

    fun onCreate(){
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = getPokedexUseCase.getPokedex()
            if (result.isNotEmpty()){
                _pokedexList.postValue(result.sortedBy {
                    it.pokemonSpecies.pokemonName
                })
                _isLoading.postValue(false)
            }
        }
    }
}