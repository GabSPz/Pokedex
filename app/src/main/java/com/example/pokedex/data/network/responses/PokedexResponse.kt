package com.example.pokedex.data.network.responses

import com.example.pokedex.data.model.PokedexModel
import com.google.gson.annotations.SerializedName

data class PokedexResponse(
    @SerializedName("name") val pokedexName: String,
    @SerializedName("pokemon_entries") val pokemonEntries: List<PokedexModel>
)
