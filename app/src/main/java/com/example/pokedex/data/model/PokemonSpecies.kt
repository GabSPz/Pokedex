package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonSpecies (
    @SerializedName("name") val pokemonName : String,
)
