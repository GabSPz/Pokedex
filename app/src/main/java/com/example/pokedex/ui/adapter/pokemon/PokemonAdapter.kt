package com.example.pokedex.ui.adapter.pokemon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel

class PokemonAdapter(
    private val evolutionList: MutableList<EvolutionPokemonModel>
    ): RecyclerView.Adapter<PokemonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.evolution_item, parent, false))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        val item = evolutionList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = evolutionList.size
    //    val cont: Int = 0
    //    while (evolutionList .isNotEmpty()){
    //
    //    }
    //}

    fun getpo(evolutionPokemonModel: EvolutionPokemonModel) {
        var flag = true
        val cont = 0
        evolutionList.clear()
        val evo = mutableListOf<EvolutionPokemonModel>()
        evo.add(evolutionPokemonModel)
        while (!flag){
            for(i in evo.indices){
                val list = evo
                evolutionList.addAll(list)
                evo.clear()
                evo.addAll(list[i].evolves)
                if (evo[i].evolves.isEmpty()){
                    flag = false
                }
            }
        }

    }
}