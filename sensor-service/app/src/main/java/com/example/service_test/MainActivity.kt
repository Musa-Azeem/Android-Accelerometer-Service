package com.example.service_test

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import com.example.service_test.databinding.ActivityMainBinding

class MainActivity : Activity(), SensorEventListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(p0: SensorEvent) {
        val timestamp: Long = p0.timestamp
        Log.d("0000",timestamp.toString())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}