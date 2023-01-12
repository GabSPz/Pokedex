package com.example.pokedex.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.model.pokedexmodel.PokedexModel
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.ui.adapter.pokedex.PokedexAdapter
import com.example.pokedex.ui.pokemon.PokemonActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: PokedexAdapter
    private val pokedexList = mutableListOf<PokedexModel>()

    private lateinit var homeViewModel: HomeViewModel

    private val pokedexFilter = mutableListOf<PokedexModel>()
    private var pokedexSize : Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.svPokedex.setOnQueryTextListener(this)

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.sflPokedex.startShimmer()
            }
            binding.sflPokedex.isVisible = it
            binding.rvPokedex.isVisible = !it
            binding.svPokedex.isVisible = !it
        })

        getPokedex()

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPokedex() {
        CoroutineScope(Dispatchers.IO).launch {
            //getting pokedex list
            homeViewModel.onCreate()

            activity?.runOnUiThread {
                homeViewModel.pokedexList.observe(
                    viewLifecycleOwner, Observer {
                        val pokedex = it ?: emptyList()
                        if (pokedex.isNotEmpty()) {
                            pokedexList.apply {
                                clear()
                                addAll(pokedex)
                            }
                            initRecyclerView(pokedexList)
                            adapter.notifyDataSetChanged()

                            //for te searchView
                            pokedexFilter.addAll(pokedex)
                            pokedexSize = pokedex.size
                        } else{
                            //show error
                            showError()
                        }
                    }
                )
            }
        }
    }

    private fun initRecyclerView(pokedexList: List<PokedexModel>){
        adapter = PokedexAdapter(pokedexList){ onItemSelected(it) }
        binding.rvPokedex.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        binding.rvPokedex.adapter = adapter
    }

    private fun onItemSelected(pokedexModel: PokedexModel){
        //go to PokemonActivity when the user click in anyone of the recycler view's items
        val intent = Intent(this.context, PokemonActivity::class.java).apply {
            putExtra("POKEMON_ID",pokedexModel.pokemonId.toString())
        }
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty() && binding.rvPokedex.size < pokedexSize){
            pokedexList.apply {
                clear()
                addAll(pokedexFilter)
            }
            initRecyclerView(pokedexList)
            adapter.notifyDataSetChanged()
        } else{
            val searchText = newText!!.lowercase()
            pokedexList.clear()

            val pokemonFilters: List<PokedexModel> = pokedexFilter.filter {
                it.pokemonSpecies.pokemonName.lowercase().contains(searchText)
            }
            print(pokemonFilters)
            pokedexList.addAll(pokemonFilters)
            initRecyclerView(pokedexList)
            adapter.notifyDataSetChanged()
        }
        return false
    }

    private fun showError(){
        Toast.makeText(this.context, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}