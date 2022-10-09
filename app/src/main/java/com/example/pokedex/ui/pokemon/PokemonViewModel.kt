package com.example.pokedex.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.domain.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUseCase: GetPokemonUseCase
): ViewModel() {
    private val _pokemon = MutableLiveData<PokemonModel>()
    private val _evolutions = MutableLiveData<EvolutionChainResponse>()
    private val _species = MutableLiveData<PokemonSpeciesModel>()
    private val _isLoading = MutableLiveData<Boolean>()

    val pokemon: LiveData<PokemonModel> = _pokemon
    val evolutions: LiveData<EvolutionChainResponse> = _evolutions
    val species :LiveData<PokemonSpeciesModel> = _species
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPokemon(pokemonId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = getPokemonUseCase.getPokemon(pokemonId)
            if (result != null) {
                _pokemon.postValue(result!!)
                _isLoading.postValue(false)
            }
        }
    }

    fun getEvolutionChain(pokemonId: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val result = getPokemonUseCase.getEvolutionChain(pokemonId)
            if (result != null){
                _evolutions.postValue(result!!)
                _isLoading.postValue(false)
            }
        }
    }

    fun getPokemonSpecies(pokemonId: String) {
        viewModelScope.launch {
            val result = getPokemonUseCase.getPokemonSpecies(pokemonId)
            if (result != null){
                _species.postValue(result!!)
            }
        }
    }

}