package com.example.pokedex.data.network.responses

import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.google.gson.annotations.SerializedName

data class EvolutionChainResponse(
    @SerializedName("id") val evolutionID: Int,
    @SerializedName("chain") val evolutions: EvolutionPokemonModel
)
