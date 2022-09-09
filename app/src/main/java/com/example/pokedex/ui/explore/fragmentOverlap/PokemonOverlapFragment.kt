package com.example.pokedex.ui.explore.fragmentOverlap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.pokedex.R
import com.example.pokedex.data.model.PokedexModel
import com.example.pokedex.databinding.FragmentPokemonOverlapBinding
import com.example.pokedex.ui.explore.ExplorerFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonOverlapFragment : Fragment() {


    private var _binding: FragmentPokemonOverlapBinding? = null

    private val binding get() = _binding!!

    private lateinit var pokemonOverlapViewModel: PokemonOverlapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pokemonOverlapViewModel =
            ViewModelProvider(this).get(PokemonOverlapViewModel::class.java)
        _binding = FragmentPokemonOverlapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        getRandomPokemon()
        binding.btnBack.setOnClickListener { closeInfo() }
        return root
    }

    private fun closeInfo(){
        activity?.supportFragmentManager?.popBackStack()
        //requireActivity().supportFragmentManager.beginTransaction().show(ExplorerFragment())
        //ExplorerFragment().closeInfo()
    }

    private fun putInfo(pokedexModel: PokedexModel) {
        binding.tvOverlap.text = " Has encontrado a ${ pokedexModel.pokemonSpecies.pokemonName }"
        Picasso.get().load(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokedexModel.pokemonId}.png"
        ).into(binding.ivOverlap)
    }

    private fun getRandomPokemon(){
        CoroutineScope(Dispatchers.IO).launch{
            pokemonOverlapViewModel.onCreate()

            activity?.runOnUiThread {
                pokemonOverlapViewModel.pokedex.observe(viewLifecycleOwner, Observer {
                    putInfo(it)
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}