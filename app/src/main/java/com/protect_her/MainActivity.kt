package com.protect_her.shehelp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != 0) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS), 1)
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val contact = findViewById<EditText>(R.id.etContact).text.toString()
            getSharedPreferences("SheProtectParams", MODE_PRIVATE).edit().putString("emergency_contact", contact).apply()
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
        }

        findViewById<MaterialCardView>(R.id.cardFakeCall).setOnClickListener {
            android.os.Handler(mainLooper).postDelayed({ startActivity(Intent(this, FakeCallActivity::class.java)) }, 5000)
            Toast.makeText(this, "Fake Call in 5s", Toast.LENGTH_SHORT).show()
        }

        findViewById<MaterialCardView>(R.id.cardService).setOnClickListener {
            startForegroundService(Intent(this, SafetyService::class.java))
            Toast.makeText(this, "Activated!", Toast.LENGTH_SHORT).show()
        }
    }
}