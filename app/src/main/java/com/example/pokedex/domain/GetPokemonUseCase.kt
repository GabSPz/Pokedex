package com.example.pokedex.domain

import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.example.pokedex.data.network.responses.EvolutionChainResponse
import com.example.pokedex.data.repository.PokedexRepository
import com.example.pokedex.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val pokedexRepo: PokedexRepository
) {
    suspend fun getPokemon(pokemonId: String): PokemonModel? {
        return pokemonRepository.getPokemon(pokemonId)
    }

    suspend fun insertFavoritePokemon(pokemonModel: PokemonModel) {
        pokemonRepository.insertFavoritePokemon(pokemonModel)
    }

    suspend fun deleteFavoritePokemon(name: String) {
        pokemonRepository.deleteFavoritePokemon(name)
    }

    suspend fun getAllFavoritesChamp(): List<PokedexModel> {
        return pokedexRepo.getAllFavoritesPokemon()
    }
}