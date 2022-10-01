package com.example.pokedex.ui.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.ui.adapter.pokemon.PokemonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonBinding
    private val pokemonViewModel: PokemonViewModel by viewModels()

    private lateinit var pokemon : PokemonModel
    private val evolutionList = mutableListOf<EvolutionPokemonModel>()
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPokemonId()
    }

    private fun getPokemonId() {
        val bundle = intent.extras
        val pokemonId = bundle?.get("POKEMON_ID") as String

        getAllPokemon(pokemonId)
    }

    fun getAllPokemon(pokemonId: String){
        CoroutineScope(Dispatchers.IO).launch {
            pokemonViewModel.getEvolutionChain(pokemonId)
            pokemonViewModel.getPokemon(pokemonId)
            runOnUiThread {
                pokemonViewModel.pokemon.observe(this@PokemonActivity, Observer {
                    pokemon = it
                })
                pokemonViewModel.evolutions.observe(this@PokemonActivity, Observer {
                    evolutionList.addAll(it)
                    initRecyclerView()
                })
            }
        }
    }

    fun initRecyclerView(){
        adapter = PokemonAdapter(evolutionList)
        binding.rvPokemonEvolution.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvPokemonEvolution.adapter = adapter
    }
}