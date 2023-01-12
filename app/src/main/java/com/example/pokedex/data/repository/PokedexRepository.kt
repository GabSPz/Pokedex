package com.example.pokedex.data.repository

import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.services.PokedexService
import javax.inject.Inject

class PokedexRepository @Inject constructor(
    private val pokedexService: PokedexService,

    ) {
    suspend fun getPokedex(): List<PokedexModel>{
        return pokedexService.getPokedex()
    }

    suspend fun getAllFavoritesPokemon(): List<PokedexModel> {
        val response = pokedexService.getFavoritesPokemons()
        return  response.map { PokedexModel(it.id, PokemonSpecies(it.name, "")) }
    }

}