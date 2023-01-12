package com.example.pokedex.data.repository

import com.example.pokedex.data.services.EvolutionService
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import javax.inject.Inject

class EvolutionRepository @Inject constructor(
    private val evolutionService: EvolutionService
) {
    suspend operator fun invoke(pokemonId: String): EvolutionChainResponse? {
        return evolutionService(pokemonId)
    }
}