package com.example.pokedex.data.network

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(pokemonId: String): PokemonModel?{
        return withContext(Dispatchers.IO){
            val response = apiService.getPokemon(pokemonId)
            response.body()
        }
    }
}