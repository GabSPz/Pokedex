package com.example.pokedex.data.repository

import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.SpeciesService
import javax.inject.Inject

class SpeciesRepository @Inject constructor(
    private val speciesService: SpeciesService
) {

    suspend operator fun invoke(pokemonId: String) : PokemonSpeciesModel? {
        return speciesService(pokemonId)
    }
}