package com.example.alertkuliahandre

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val stopButton: Button = findViewById(R.id.stopButton)

        startButton.setOnClickListener {
            val serviceIntent = Intent(this, NotificationService::class.java)
            startForegroundService(serviceIntent)
        }

        stopButton.setOnClickListener {
            val serviceIntent = Intent(this, NotificationService::class.java)
            stopService(serviceIntent)
        }
    }
}
