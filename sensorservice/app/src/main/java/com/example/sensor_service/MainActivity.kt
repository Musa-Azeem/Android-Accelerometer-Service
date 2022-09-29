package com.example.sensor_service

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.Bundle
import android.util.Log
import com.example.sensor_service.databinding.ActivityMainBinding


class MainActivity : Activity(){

    private lateinit var binding: ActivityMainBinding
//    private lateinit var mSensorManager: SensorManager
//    private lateinit var mAccelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // start service
        val accelIntent = Intent(applicationContext, AccelLoggerService::class.java)
        startForegroundService(accelIntent)

    }
    override fun onPause(){
        super.onPause()
        Log.i("1", "PAUSED")
    }
    override fun onStop(){
        super.onStop()
        Log.i("1", "STOPPED")

//    override fun onSensorChanged(p0: SensorEvent) {
//        val timestamp: Long = p0.timestamp
//        Log.d("0000",timestamp.toString())
//    }
//
//    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//    }
    }
}