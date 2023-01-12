package com.example.pokedex.data.services

import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.database.entities.PokemonEntity
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokedexService @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao
) {
    suspend fun getPokedex(): List<PokedexModel> {
        return withContext(Dispatchers.IO){
            val result = apiService.getPokedex()
            result.body()?.pokemonEntries ?: emptyList()
        }
    }

    suspend fun getFavoritesPokemons(): List<PokemonEntity> {
        return pokemonDao.getAllFavoritesPokemon()
    }
}