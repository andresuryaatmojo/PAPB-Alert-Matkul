package com.example.alertkuliahandre

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Notifikasi ditunda selama 10 menit", Toast.LENGTH_SHORT).show()

        // Logika untuk menunda notifikasi
    }
}
