package com.example.pokedex.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.domain.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: FavoritesUseCase
): ViewModel() {
    private val _pokemons = MutableLiveData<List<PokedexModel>>()

    val pokemons: LiveData<List<PokedexModel>> = _pokemons
    val isLoading = MutableLiveData<Boolean>()

    suspend fun getAllFavoritesPokemons() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = useCase.getAllFavoritesChamp()
            _pokemons.postValue(result)
            isLoading.postValue(false)
        }
    }

}