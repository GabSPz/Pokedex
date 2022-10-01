package com.example.pokedex.ui.adapter.pokedex

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.core.extensions.firstCharUpper
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.databinding.PokedexItemBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class PokedexViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = PokedexItemBinding.bind(view)
    private lateinit var image: RequestCreator

    fun render(pokedexModel: PokedexModel, onCLick: (PokedexModel) -> Unit) {

        Picasso.get().load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokedexModel.pokemonId}.png").into(binding.ivPokedexImage)
        binding.tvPokedexName.text = pokedexModel.pokemonSpecies.pokemonName.firstCharUpper()
        itemView.setOnClickListener { onCLick(pokedexModel) }
    }
}