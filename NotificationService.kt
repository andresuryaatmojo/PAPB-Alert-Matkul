package com.example.alertkuliahandre

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.*

class NotificationService : Service() {

    private val CHANNEL_ID = "AlertKuliahAndreChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()
        scheduleNotifications()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alert Kuliah Andre Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startForegroundService() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Alert Kuliah Andre")
            .setContentText("Service berjalan untuk mengingatkan jadwal kuliah.")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    private fun scheduleNotifications() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Jadwal kuliah statis
        val schedules = listOf(
            Triple(Calendar.MONDAY, "Komputasi Terdistribusi dan Cloud", "07:30"),
            Triple(Calendar.MONDAY, "Pengembangan Aplikasi Perangkat Bergerak", "10:01"),
            Triple(Calendar.MONDAY, "Proposal Tugas Akhir", "13:00"),
            Triple(Calendar.TUESDAY, "Analisis dan Desain Sistem Informasi", "07:29"),
            Triple(Calendar.TUESDAY, "Etika Profesi dan Kuliah Kerja Lapangan", "10:52"),
            Triple(Calendar.TUESDAY, "Big Data dan Analitik", "15:31"),
            Triple(Calendar.WEDNESDAY, "Perencanaan Teknologi Informasi", "07:30"),
            Triple(Calendar.THURSDAY, "Pemrograman Berorientasi Objek", "13:00"),
            Triple(Calendar.THURSDAY, "Manajemen dan Ekonomi Teknik (MET)", "15:31")
        )

        for ((day, subject, time) in schedules) {
            val timeParts = time.split(":").map { it.toInt() }
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, day)
                set(Calendar.HOUR_OF_DAY, timeParts[0](citation_0))
                set(Calendar.MINUTE, timeParts[1](citation_1))
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (calendar.timeInMillis < System.currentTimeMillis()) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }

            val intent = Intent(this, NotificationReceiver::class.java).apply {
                putExtra("subject", subject)
                putExtra("time", time)
            }
            val pendingIntent = PendingIntent.getBroadcast(this, day * 100 + timeParts[0](citation_0), intent, PendingIntent.FLAG_UPDATE_CURRENT)

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                pendingIntent
            )
        }
    }
}
