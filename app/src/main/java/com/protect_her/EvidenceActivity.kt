package com.protect_her.shehelp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class EvidenceActivity : AppCompatActivity() {
    private lateinit var recorder: EvidenceRecorder
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize & Start Siren
        recorder = EvidenceRecorder(this)
        mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // 2. Start Recording
        recorder.startRecording(this)

        // 3. Get REAL-TIME Location & Send SMS
        getRealTimeLocationAndSendSMS()

        // 4. Stop everything after 30 seconds
        object : CountDownTimer(30000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                finishEvidence()
            }
        }.start()
    }

    private fun getRealTimeLocationAndSendSMS() {
        // Permission Check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location Permission Missing!", Toast.LENGTH_SHORT).show()
            return
        }

        val fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        val cancellationToken = CancellationTokenSource()

        // FORCE a fresh, high-accuracy update (This is the "Real-Time" magic)
        fusedLocation.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token)
            .addOnSuccessListener { location ->
                if (location != null) {
                    // We got a real GPS fix!
                    sendSMS(location.latitude, location.longitude)
                } else {
                    // GPS failed or is off. Send SMS saying "Location Unavailable"
                    sendSMS(0.0, 0.0)
                }
            }
            .addOnFailureListener {
                // Technical error getting location
                sendSMS(0.0, 0.0)
            }
    }

    private fun sendSMS(lat: Double, lng: Double) {
        val prefs = getSharedPreferences("SheProtectParams", Context.MODE_PRIVATE)
        val contactNumber = prefs.getString("emergency_contact", "")

        if (!contactNumber.isNullOrEmpty()) {
            val smsManager = SmsManager.getDefault()
            val message: String

            if (lat == 0.0 && lng == 0.0) {
                // Fallback message if GPS failed
                message = "HELP! I am in an emergency! My GPS is not responding, but please call me immediately."
            } else {
                // The Working Google Maps Link
                val mapsLink = "https://maps.google.com/?q=$lat,$lng"
                message = "HELP! I am in an emergency! Track me here: $mapsLink"
            }

            try {
                // Send the text
                smsManager.sendTextMessage(contactNumber, null, message, null, null)
                runOnUiThread {
                    Toast.makeText(this, "SOS Sent to $contactNumber", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "SMS Failed: Check SIM Card", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this, "No Emergency Contact Saved!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun finishEvidence() {
        try {
            recorder.stopRecording()
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        finishEvidence()
    }
}