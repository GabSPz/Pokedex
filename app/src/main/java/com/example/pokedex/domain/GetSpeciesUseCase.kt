package com.example.pokedex.domain

import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.repository.SpeciesRepository
import javax.inject.Inject

class GetSpeciesUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository
){

    suspend operator fun invoke(pokemonId: String): PokemonSpeciesModel? {
        return speciesRepository(pokemonId)
    }
}