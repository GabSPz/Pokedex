package com.example.pokedex.ui.explore.fragmentOverlap

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentDashboardBinding
import com.example.pokedex.databinding.FragmentPokemonOverlapBinding
import com.google.android.gms.maps.GoogleMap

class PokemonOverlapFragment : Fragment() {

    companion object {
        fun newInstance() = PokemonOverlapFragment()
    }

    private var _binding: FragmentPokemonOverlapBinding? = null


    private val binding get() = _binding!!

    private lateinit var viewModel: PokemonOverlapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonOverlapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PokemonOverlapViewModel::class.java)
        // TODO: Use the ViewModel
    }

}