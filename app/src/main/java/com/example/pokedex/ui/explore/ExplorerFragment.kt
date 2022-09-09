package com.example.pokedex.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.data.model.uiModel.UiModel
import com.example.pokedex.databinding.FragmentExploreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExplorerFragment : Fragment(),
    OnMapReadyCallback{

    private var _binding: FragmentExploreBinding? = null

    private lateinit var maps: GoogleMap

    private lateinit var explorerViewModel: ExplorerViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         explorerViewModel =
            ViewModelProvider(this).get(ExplorerViewModel::class.java)

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        createMapInstance()

        binding.btnStart.setOnClickListener{ onStartTravel()}

        explorerViewModel.onCreate(activity = requireActivity())
        return root
    }

    private fun onStartTravel(){
        if (binding.btnStart.text == getString(R.string.start_tracking)) {
            //1
            startTracking()
            binding.btnStart.setText(R.string.stop_tracking)
        } else {
            //2
            stopTracking()
            binding.btnStart.setText(R.string.start_tracking)
        }
    }






    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap
        //maps.setOnMyLocationClickListener { this }
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
            maps.animateCamera(CameraUpdateFactory.newLatLngZoom(uiModel.currentLocation, 18f))
        }
        binding.tvDistance.text = uiModel.formattedDistance
        drawTravelTrack(uiModel.userTrack)
    }

    private fun drawTravelTrack(userTrack: List<LatLng>) {

        val polylineOptions = PolylineOptions()

        maps.clear()

        val points =polylineOptions.points
        points.addAll(userTrack)

        maps.addPolyline(polylineOptions)
    }

    private fun createMapInstance(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    //@SuppressLint("MissingPermission")
    //private fun enableLocation() {
    //    if (!::maps.isInitialized) return
    //    if (isLocationPermissionGranted()) {
    //        maps.isMyLocationEnabled = true
    //    } else {
    //        requestLocationPermission()
    //    }
    //}
    //@SuppressLint("UseRequireInsteadOfGet")
    //private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
    //    requireContext(),
    //    Manifest.permission.ACCESS_FINE_LOCATION
    //) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
    //    requireContext() ,
    //    Manifest.permission.ACCESS_COARSE_LOCATION
    //) == PackageManager.PERMISSION_GRANTED
//
    //@SuppressLint("UseRequireInsteadOfGet")
    //private fun requestLocationPermission() {
    //    if (ActivityCompat.shouldShowRequestPermissionRationale(
    //            requireContext() as Activity, Manifest.permission.ACCESS_FINE_LOCATION
    //        ) && ActivityCompat.shouldShowRequestPermissionRationale(
    //            requireContext() as Activity, Manifest.permission.ACCESS_COARSE_LOCATION
    //        )
    //    ) {
    //        showPermissionDenied()
    //    }else{
    //        ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(
    //            Manifest.permission.ACCESS_FINE_LOCATION,
    //            Manifest.permission.ACCESS_COARSE_LOCATION),
    //            RequestCodeLocation.REQUEST_CODE_LOCATION
    //        )
    //    }
    //}
//
    //@SuppressLint("MissingPermission")
    //override fun onRequestPermissionsResult(
    //    requestCode: Int,
    //    permissions: Array<out String>,
    //    grantResults: IntArray
    //) {
    //    when(requestCode){
    //        RequestCodeLocation.REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
    //            maps.isMyLocationEnabled = true
    //        } else{
    //            showPermissionDenied()
    //        }
    //        else -> {}
    //    }
    //}
//
    //@SuppressLint("MissingPermission")
    //override fun onResume() {
    //    super.onResume()
    //    if (!::maps.isInitialized) return
    //    if(!isLocationPermissionGranted()){
    //        maps.isMyLocationEnabled = false
    //        showPermissionDenied()
    //    }
    //}
//
//
    //private fun getDistanceTraveled(distances: Float){
    //    if (distances >= 100){
//
    //    }
    //   //var distanceTotal = 0f
    //   //var distanceX: Float = 0f
    //   //var distanceY: Float = 0f
    //   //var distanceZ: Float = 0f
    //   //when (distanceTotal){
    //   //    distanceX, distanceY, distanceZ -> if (distanceTotal <100f){
    //   //
    //   //    }
    //   //}
    //   //distances.forEach{
    //   //    distanceX += it[0]
    //   //    distanceY += it[1]
    //   //    distanceZ += it[2]
    //   //}
//
    //}
//
    //private fun showPermissionDenied(){
    //    Toast.makeText(
    //        this.context,
    //        "Ve a los ajustes y acepta los permisos para poder utilizar esta función",
    //        Toast.LENGTH_SHORT
    //    ).show()
    //}
//
    //override fun onDestroyView() {
    //    super.onDestroyView()
    //    _binding = null
    //}
//
//
//
    //override fun onMyLocationClick(location: Location) {
//
    //    Toast.makeText(this.context, "Estas en ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
    //}
//
//
//
    //private fun getDistanceRun(steps: Long): Float {
    //    return (steps * 78).toFloat() / 100000.toFloat()
    //}


}