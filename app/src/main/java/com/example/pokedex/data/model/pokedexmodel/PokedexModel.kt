package com.example.pokedex.data.model.pokedexmodel

import com.google.gson.annotations.SerializedName

data class PokedexModel(
    @SerializedName("entry_number") val pokemonId: Int,
    @SerializedName("pokemon_species") val pokemonSpecies: PokemonSpecies
)
