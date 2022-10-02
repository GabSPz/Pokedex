package com.example.pokedex.data.network

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPokemon(pokemonId: String): PokemonModel?{
        return withContext(Dispatchers.IO){
            val response = apiService.getPokemon(pokemonId)
            response.body()
        }
    }

    suspend fun getEvolutionChain(pokemonId: String): EvolutionChainResponse?{
        return withContext(Dispatchers.IO){
            val response = apiService.getEvolutionChain(pokemonId)
            response.body()
        }
    }
}