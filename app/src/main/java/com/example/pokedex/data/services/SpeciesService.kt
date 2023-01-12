package com.example.pokedex.data.services

import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpeciesService @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(pokemonId: String): PokemonSpeciesModel?{
        return withContext(Dispatchers.IO){
            val response = apiService.getPokemonSpecies(pokemonId)
            response.body()?.evolutionChain
        }
    }
}