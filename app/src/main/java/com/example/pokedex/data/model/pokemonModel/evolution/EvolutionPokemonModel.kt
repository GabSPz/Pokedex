package com.example.pokedex.data.model.pokemonModel.evolution

import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.google.gson.annotations.SerializedName

data class EvolutionPokemonModel(
    @SerializedName ("evolution_details") val evolutionDetail: EvolutionDetail,
    @SerializedName ("species") val pokemonEvo: PokemonSpecies,
    //@SerializedName("evolves_to") val evolutions: List<EvolutionPokemonModel>
)
