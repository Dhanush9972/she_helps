package com.protect_her.shehelp

import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FakeCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_call) // Ensure this XML exists!

        val ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
        ringtone.play()

        findViewById<android.view.View>(R.id.btnDecline).setOnClickListener { ringtone.stop(); finish() }
    }
}