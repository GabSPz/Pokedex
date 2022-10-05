package com.example.pokedex.data.model.pokemonSpeciesModel

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesModel (
    @SerializedName ("url") val speciesUrl: String
        )