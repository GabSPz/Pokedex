package com.example.pokedex.data.model.pokemonModel.evolution

import com.google.gson.annotations.SerializedName

data class EvolutionDetail(
    @SerializedName("min_level") val minLevel: Int
)
