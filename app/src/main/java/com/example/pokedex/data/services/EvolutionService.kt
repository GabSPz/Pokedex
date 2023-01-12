package com.example.pokedex.data.services

import com.example.pokedex.data.network.ApiService
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EvolutionService @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(pokemonId: String): EvolutionChainResponse?{
        return withContext(Dispatchers.IO){
            val response = apiService.getEvolutionChain(pokemonId)
            response.body()
        }
    }
}