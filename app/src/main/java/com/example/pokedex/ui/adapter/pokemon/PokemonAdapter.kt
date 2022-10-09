package com.example.pokedex.ui.adapter.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.pokedexmodel.PokemonSpecies
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel

class PokemonAdapter(
    private val evolutionList: List<EvolutionPokemonModel>,
    private val onCLick: (PokemonSpecies) -> Unit
) : RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.evolution_item, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = evolutionList[position]
        holder.render(item, onCLick)
    }

    override fun getItemCount(): Int = evolutionList.size
}