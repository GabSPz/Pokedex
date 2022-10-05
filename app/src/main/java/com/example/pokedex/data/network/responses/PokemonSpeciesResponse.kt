package com.example.pokedex.data.network.responses

import com.example.pokedex.data.model.pokemonSpeciesModel.PokemonSpeciesModel
import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse (
    @SerializedName ("evolution_chain") val evolutionChain : PokemonSpeciesModel
)
