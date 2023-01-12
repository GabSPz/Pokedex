package com.example.pokedex.data.repository

import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.services.PokemonService
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonService: PokemonService
) {

    suspend  fun getPokemon(pokemonId: String): PokemonModel?{
        return pokemonService.getPokemon(pokemonId)
    }

    suspend fun insertFavoritePokemon(pokemonModel: PokemonModel) {
        pokemonService.insertFavoritePokemon(pokemonModel)
    }
    suspend fun deleteFavoritePokemon(name: String) {
        pokemonService.deleteFavoritePokemon(name)
    }


}
