package com.example.pokedex.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.databinding.FragmentFavoriteBinding
import com.example.pokedex.ui.adapter.pokedex.PokedexAdapter
import com.example.pokedex.ui.pokemon.PokemonActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment: Fragment() {

    private var _binding: FragmentFavoriteBinding? = null


    private lateinit var adapter: PokedexAdapter
    private val pokemons = mutableSetOf<PokedexModel>()

    private lateinit var favoritesViewModel: FavoriteViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoritesViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root = binding.root

        favoritesViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.sflPokedex.startShimmer()
            binding.sflPokedex.isVisible = it
            binding.rvPokedex.isVisible = !it
        }
        getFavoritePokemons()
        return root
    }

    private fun getFavoritePokemons() {
        CoroutineScope(Dispatchers.IO).launch {
            favoritesViewModel.getAllFavoritesPokemons()

            activity?.runOnUiThread {
                favoritesViewModel.pokemons.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        pokemons.addAll(it)
                        initRecyclerView()
                        adapter.notifyDataSetChanged()
                    }
                    binding.tvError.isVisible = it.isEmpty()

                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = PokedexAdapter(pokemons.toList()) { onItemSelected(it) }
        binding.rvPokedex.layoutManager = LinearLayoutManager(this.context)
        binding.rvPokedex.adapter = adapter
    }

    private fun onItemSelected(pokedexModel: PokedexModel){
        //go to PokemonActivity when the user click in anyone of the recycler view's items
        val intent = Intent(this.context, PokemonActivity::class.java).apply {
            putExtra("POKEMON_ID",pokedexModel.pokemonId.toString())
        }
        startActivity(intent)
    }
    override fun onDestroyView() {
        _binding = null
        pokemons.clear()
        super.onDestroyView()

    }
}