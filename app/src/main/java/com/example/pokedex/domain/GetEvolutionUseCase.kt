package com.example.pokedex.domain

import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.data.repository.EvolutionRepository
import javax.inject.Inject

class GetEvolutionUseCase @Inject constructor(
    private val evolutionRepository: EvolutionRepository
) {
    suspend operator fun invoke(pokemonId: String): EvolutionChainResponse? {
        return evolutionRepository(pokemonId)
    }
}