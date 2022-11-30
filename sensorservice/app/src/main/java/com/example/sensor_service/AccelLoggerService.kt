package com.example.sensor_service

import android.app.*
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import android.app.NotificationManager


class AccelLoggerService: Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private val NOTIFICATION_CHANNEL_ID = "Channel_1"


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

        // Start foreground service
        startForeground(1, createNotification())

        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent) {
        var x = event.values[0]
        var y = event.values[1]
        var z = event.values[2]
        var time = event.timestamp
        Log.i("service", "time: $time    x: $x     y: $y    z: $z")
        // var async = SensorEventLoggerTask()
        // async.execute(event)
    }
//    private class SensorEventLoggerTask :AsyncTask<SensorEvent, Unit, Unit>() {
//        override fun doInBackground(vararg events: SensorEvent) {
//
//            for(event in events){
//                var x = event.values[0]
//                var y = event.values[1]
//                var z = event.values[2]
//                var time = event.timestamp
//                Log.i("service", "time: $time    x: $x     y: $y    z: $z")
//            }
//        }
//    }
    private fun createNotification(): Notification {
        // Create the NotificationChannel
        val channelName = "foreground_service_channel"
        val channelDescription = "Notifications for foreground service"
        val mChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        mChannel.description = channelDescription
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        // Create Notification
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        return Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Delta")
            .setContentText("Accelerometer Recording")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setTicker("Ticker")
            .build()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("service", "DESTROYED")
        sensorManager.unregisterListener(this)
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing
    }

    companion object {
        private const val DEBUG_TAG = "AccelLoggerService"
    }
}