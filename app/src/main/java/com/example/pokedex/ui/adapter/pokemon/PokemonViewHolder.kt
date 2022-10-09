package com.example.pokedex.ui.adapter.pokemon

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.core.extensions.getPokemonIdByUrl
import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.databinding.EvolutionItemBinding
import com.squareup.picasso.Picasso

class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = EvolutionItemBinding.bind(view)

    fun render(evolutionPokemonModel: EvolutionPokemonModel, onCLick: (PokemonSpecies) -> Unit) {
        Picasso.get().load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${
                evolutionPokemonModel.pokemonEvo.url.getPokemonIdByUrl()
            }.png"
        ).into(binding.ivEvolution)
        itemView.setOnClickListener { onCLick(evolutionPokemonModel.pokemonEvo) }
    }

}