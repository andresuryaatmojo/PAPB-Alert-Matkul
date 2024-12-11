package com.example.alertkuliahandre

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val subject = intent.getStringExtra("subject") ?: "Jadwal Kuliah"
        val time = intent.getStringExtra("time") ?: ""

        val snoozeIntent = Intent(context, SnoozeReceiver::class.java)
        val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, 0)

        val notification = NotificationCompat.Builder(context, "AlertKuliahAndreChannel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Pengingat Kuliah")
            .setContentText("Kuliah: $subject pada $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.ic_snooze, "Tunda", snoozePendingIntent)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(subject.hashCode(), notification)
        }
    }
}
