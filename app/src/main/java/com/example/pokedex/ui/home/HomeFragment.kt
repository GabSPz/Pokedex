package com.example.pokedex.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.data.model.PokedexModel
import com.example.pokedex.databinding.FragmentHomeBinding
import com.example.pokedex.ui.adapter.PokedexAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

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
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.sflPokedex.startShimmer()

        getPokedex()
        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.sflPokedex.isVisible = it
            binding.rvPokedex.isVisible = !it
        })

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPokedex() {
        CoroutineScope(Dispatchers.IO).launch {
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
                            pokedexSize = pokedexFilter.size
                        } else{
                            //show error
                        }
                    }
                )
            }
        }
    }

    private fun initRecyclerView(pokedexList: List<PokedexModel>){
        adapter = PokedexAdapter(pokedexList){}
        binding.rvPokedex.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        binding.rvPokedex.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}