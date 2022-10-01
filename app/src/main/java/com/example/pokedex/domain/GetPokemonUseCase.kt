package com.example.pokedex.domain

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend fun getPokemon(pokemonId: String): PokemonModel? {
        return pokemonRepository.getPokemon(pokemonId)
    }

    suspend fun getEvolutionChain(pokemonId: String): List<EvolutionPokemonModel> {
        return pokemonRepository.getEvolutionChain(pokemonId)
    }
}