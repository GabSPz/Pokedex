package com.example.pokedex.domain

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.data.repository.PokedexRepository
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(private val pokedexRepository: PokedexRepository) {

    suspend fun getAllFavoritesChamp(): List<PokedexModel> {
        return pokedexRepository.getAllFavoritesPokemon()
    }
}