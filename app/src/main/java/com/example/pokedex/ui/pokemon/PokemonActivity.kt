package com.example.pokedex.ui.pokemon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.core.extensions.firstCharUpper
import com.example.pokedex.core.extensions.getPokemonIdByUrl
import com.example.pokedex.data.model.pokemonModel.PokemonModel
import com.example.pokedex.data.model.pokemonModel.evolution.EvolutionPokemonModel
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.ui.adapter.pokemon.PokemonAdapter
import com.squareup.picasso.Picasso
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
        pokemonViewModel.isLoading.observe(this, Observer {
            binding
        })
        getPokemonId()
    }

    private fun getPokemonId() {
        val bundle = intent.extras
        val pokemonId = bundle?.get("POKEMON_ID") as String

        getPokemon(pokemonId)
    }

    private fun getPokemon(pokemonId: String){
        CoroutineScope(Dispatchers.IO).launch {
            pokemonViewModel.getPokemon(pokemonId)
            pokemonViewModel.getPokemonSpecies(pokemonId)
            runOnUiThread {
                pokemonViewModel.pokemon.observe(this@PokemonActivity, Observer {
                    pokemon = it
                    putPokemonInfo()
                })
                pokemonViewModel.species.observe(this@PokemonActivity, Observer {
                    if(it != null){
                        getEvolutions(it.speciesUrl.getPokemonIdByUrl())
                    }
                } )
            }
        }
    }

    private fun getEvolutions(id:String) {
        CoroutineScope(Dispatchers.IO).launch {
            pokemonViewModel.getEvolutionChain(id)
            runOnUiThread {
                pokemonViewModel.evolutions.observe(this@PokemonActivity, Observer {
                    evolutionList.addAll(it.evolutions.evolves)
                    initRecyclerView()
                })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun putPokemonInfo() {
        Picasso.get().load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.pokemonId}.png"
        ).into(binding.ivPokemonImage)
        binding.tvPokemonName.text = pokemon.pokemonName.firstCharUpper()
        binding.tvContainer1.text = "Height: ${pokemon.height} ft"
        binding.tvContainer2.text = "Weight: ${pokemon.weight} lb"
    }

    private fun initRecyclerView(){
        adapter = PokemonAdapter(evolutionList)
        binding.rvPokemonEvolution.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvPokemonEvolution.adapter = adapter
    }
}