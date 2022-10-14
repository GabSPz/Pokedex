package com.example.pokedex.data.repository

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.PokemonService
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) {

    suspend operator fun invoke(pokemonId: String): PokemonModel?{
        return pokemonService(pokemonId)
    }
}
