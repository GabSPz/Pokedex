package com.example.pokedex.data.maps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class StepCounter @Inject constructor(
    private val activity: FragmentActivity
) :SensorEventListener {

    val liveSteps = MutableLiveData<Int>()
    private var initialSteps = -1


    private val sensorManager by lazy {
        activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val stepCounterSensor: Sensor? by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onSensorChanged(event: SensorEvent) {
        event.values.firstOrNull()?.toInt()?.let {
            if (initialSteps == -1){
                initialSteps = it
            }
            val currentSteps = it - initialSteps

            liveSteps.value =currentSteps
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    fun stepCounterConfig() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    fun stepCounterRelease() {
        if (stepCounterSensor != null) {
            sensorManager.unregisterListener(this)
        }
    }
}