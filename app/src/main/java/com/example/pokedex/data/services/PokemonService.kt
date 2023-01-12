package com.example.pokedex.data.services

import com.example.pokedex.data.database.dao.PokemonDao
import com.example.pokedex.data.database.entities.PokemonEntity
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDao: PokemonDao

) {
    suspend fun getPokemon(pokemonId: String): PokemonModel?{
        return withContext(Dispatchers.IO){
            val response = apiService.getPokemon(pokemonId)
            response.body()
        }
    }

    suspend fun insertFavoritePokemon(pokemon: PokemonModel) {
        pokemonDao.insertPokemon(PokemonEntity(name = pokemon.pokemonName, id = pokemon.pokemonId))
    }

    suspend fun deleteFavoritePokemon(name: String) {
        pokemonDao.deletePokemon(pokemonName = name)
    }
}