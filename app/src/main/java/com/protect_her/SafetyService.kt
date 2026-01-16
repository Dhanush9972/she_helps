package com.protect_her.shehelp

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.math.sqrt

class SafetyService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel("SafetyChannel", "Safety", NotificationManager.IMPORTANCE_LOW)
        (getSystemService(NotificationManager::class.java)).createNotificationChannel(channel)
        startForeground(1, NotificationCompat.Builder(this, "SafetyChannel").setContentTitle("She Protect Active").setSmallIcon(android.R.drawable.ic_menu_call).build())

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]; val y = it.values[1]; val z = it.values[2]
            val acceleration = sqrt((x*x + y*y + z*z).toDouble()).toFloat()
            if (acceleration > 20) { // Shake detected
                val intent = Intent(this, EvidenceActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
    override fun onAccuracyChanged(s: Sensor?, a: Int) {}
    override fun onBind(i: Intent?): IBinder? = null
}