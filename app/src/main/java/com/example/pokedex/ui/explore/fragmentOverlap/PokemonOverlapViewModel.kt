package com.example.pokedex.ui.explore.fragmentOverlap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.PokedexModel
import com.example.pokedex.domain.GetPokedexUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonOverlapViewModel @Inject constructor(
    private val getPokedexUseCase: GetPokedexUseCase
): ViewModel() {

    private val _pokedex = MutableLiveData<PokedexModel>()
    val pokedex: LiveData<PokedexModel> = _pokedex

    fun onCreate(){
        viewModelScope.launch {
            val result = getPokedexUseCase.getPokedex()
            if (result.isNotEmpty()){
                val randomPokemon = result.random()
                _pokedex.postValue(randomPokemon)
            }
        }
    }

}