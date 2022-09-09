package com.example.pokedex.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.databinding.FragmentExploreBinding
import com.example.pokedex.ui.explore.fragmentOverlap.PokemonOverlapFragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
class ExplorerFragment : Fragment(),
    OnMapReadyCallback{

    private var _binding: FragmentExploreBinding? = null

    private lateinit var maps: GoogleMap

    private  var explorerViewModel = ExplorerViewModel(this)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fragmentCnt.isVisible = false

        createMapInstance()
        explorerViewModel.onCreate()

        binding.btnStart.setOnClickListener{ onStartTravel()}
        binding.btnGetPokemon.setOnClickListener { onGetPokemon() }
        checkDistance()
        return root
    }

    private fun checkDistance(){

        explorerViewModel.getDistance()
        explorerViewModel.distance.observe(viewLifecycleOwner, Observer {
            if (it > 100){
                onGetPokemon()
            }
        })
    }

    private fun onGetPokemon(){
        childFragmentManager.beginTransaction()
            .show(PokemonOverlapFragment())
        binding.fragmentCnt.isVisible = true
    }

    private fun onStartTravel(){
        if (binding.btnStart.text == getString(R.string.start_tracking)) {

            startTracking()
            binding.btnStart.setText(R.string.stop_tracking)
        } else {

            stopTracking()
            binding.btnStart.setText(R.string.start_tracking)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap
        explorerViewModel.ui.observe(this, Observer {
            updateUi(it)
        })

        explorerViewModel.onMapLoaded()
        maps.uiSettings.isZoomControlsEnabled = true

    }
    private fun startTracking() {
        binding.tvDistance.text = ""
        maps.clear()

        explorerViewModel.onStartExplore()
    }

    private fun stopTracking(){
        explorerViewModel.onStopExplore()
    }

    @SuppressLint("MissingPermission")
    private fun updateUi(uiModel: UiModel) {
        if (uiModel.currentLocation != null && uiModel.currentLocation != maps.cameraPosition.target){
            maps.isMyLocationEnabled = true
            maps.animateCamera(CameraUpdateFactory.newLatLngZoom(uiModel.currentLocation, 15f))
        }
        binding.tvDistance.text = uiModel.formattedDistance
        drawTravelTrack(uiModel.userTrack)
    }

    private fun drawTravelTrack(userTrack: List<LatLng>) {
        val polylineOptions = PolylineOptions()
        val points =polylineOptions.points

        maps.clear()
        points.addAll(userTrack)
        maps.addPolyline(polylineOptions)
    }

    private fun createMapInstance(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}