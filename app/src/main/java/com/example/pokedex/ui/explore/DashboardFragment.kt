package com.example.pokedex.ui.explore

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.R
import com.example.pokedex.core.RequestCodeLocation
import com.example.pokedex.databinding.FragmentDashboardBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class DashboardFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener,
    SensorEventListener{

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var maps: GoogleMap
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var steps: Long = 0
    private val distance = mutableListOf<FloatArray>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        createMapInstance()

        return root
    }






    override fun onMapReady(googleMap: GoogleMap) {
        maps = googleMap
        enableLocation()
        maps.setOnMyLocationClickListener { this }
    }

    private fun createMapInstance(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (!::maps.isInitialized) return
        if (isLocationPermissionGranted()) {
            maps.isMyLocationEnabled = true
            getSensor()
        } else {
            requestLocationPermission()
        }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        requireContext() ,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("UseRequireInsteadOfGet")
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireContext() as Activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                requireContext() as Activity, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            showPermissionDenied()
        }else{
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
                RequestCodeLocation.REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            RequestCodeLocation.REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                maps.isMyLocationEnabled = true
            } else{
                showPermissionDenied()
            }
            else -> {}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (!::maps.isInitialized) return
        if(!isLocationPermissionGranted()){
            maps.isMyLocationEnabled = false
            showPermissionDenied()
        }
    }

    private fun getSensor(){
        Log.d(TAG,"getDistanceTraveled: Initializing Sensor Services")
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        Log.d(TAG,"getDistanceTraveled: Register accelerometer listener")
    }

    private fun getDistanceTraveled(distances: Float){
        if (distances >= 100){

        }
       //var distanceTotal = 0f
       //var distanceX: Float = 0f
       //var distanceY: Float = 0f
       //var distanceZ: Float = 0f
       //when (distanceTotal){
       //    distanceX, distanceY, distanceZ -> if (distanceTotal <100f){
       //
       //    }
       //}
       //distances.forEach{
       //    distanceX += it[0]
       //    distanceY += it[1]
       //    distanceZ += it[2]
       //}

    }

    private fun showPermissionDenied(){
        Toast.makeText(
            this.context,
            "Ve a los ajustes y acepta los permisos para poder utilizar esta función",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onMyLocationClick(location: Location) {

        Toast.makeText(this.context, "Estas en ${location.latitude}, ${location.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        val values = event?.values
        var value = -1

        if (values != null && values.size ?: 0 > 0) {
            value = values[0].toInt()
        }


        if (sensor != null &&
            sensor.type == Sensor.TYPE_STEP_DETECTOR
        ) {
            val finalSteps = getDistanceRun(steps)

            getDistanceTraveled(finalSteps)
            steps++
        }
    }

    private fun getDistanceRun(steps: Long): Float {
        return (steps * 78).toFloat() / 100000.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}