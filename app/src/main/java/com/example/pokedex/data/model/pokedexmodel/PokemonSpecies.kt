package com.example.pokedex.data.model.pokedexmodel

import com.google.gson.annotations.SerializedName

data class PokemonSpecies (
    @SerializedName("name") val pokemonName : String,
    @SerializedName("url") val url: String
)
