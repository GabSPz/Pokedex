package com.example.pokedex.data.model.pokemonModel

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @SerializedName ("name") val pokemonName: String,
    @SerializedName ("height") val height: Int,
    @SerializedName ("weight") val weight: Int,
    @SerializedName ("id") val pokemonId: Int
)
