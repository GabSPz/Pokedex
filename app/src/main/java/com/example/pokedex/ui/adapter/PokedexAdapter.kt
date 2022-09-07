package com.example.pokedex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.PokedexModel
import com.example.pokedex.data.model.PokemonSpecies

class PokedexAdapter(
    private val pokedexList: List<PokedexModel>,
    private val onCLick:(PokemonSpecies)-> Unit
): RecyclerView.Adapter<PokedexViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokedexViewHolder {
        val layoutInflater =LayoutInflater.from(parent.context)
        return PokedexViewHolder(layoutInflater.inflate(R.layout.pokedex_item, parent, false))
    }

    override fun onBindViewHolder(holder: PokedexViewHolder, position: Int) {
        val item = pokedexList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = pokedexList.size
}