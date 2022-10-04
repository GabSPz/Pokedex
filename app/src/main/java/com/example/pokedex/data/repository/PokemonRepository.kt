package com.example.pokedex.data.repository

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.network.PokemonService
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) {

    suspend fun getPokemon(pokemonId: String): PokemonModel?{
        return pokemonService.getPokemon(pokemonId)
    }

    suspend fun getEvolutionChain(pokemonId: String): EvolutionChainResponse? {
        return pokemonService.getEvolutionChain(pokemonId)
    }
}
