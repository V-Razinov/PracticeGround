package ru.practiceground.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.practiceground.R
import ru.practiceground.presentation.main.MainActivity
import ru.practiceground.presentation.service.MyService.Companion.NOTIF_CONTENT

class MyBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val RECEIVER_ACTION = "ru.practiceground.action.NOTIFICATION"
        const val BROADCAST_CHANNEL_ID = "123123"
        const val BROADCAST_NOTIFICATION = 1
    }

    private var notificationManager: NotificationManager? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        val text = intent.extras?.getString(NOTIF_CONTENT) ?: return
        if (intent.action == RECEIVER_ACTION) {
            showNotification(context, text)
        }
    }

    private fun getNotificationManager(context: Context): NotificationManager {
        if (notificationManager == null) {
            notificationManager = context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    BROADCAST_CHANNEL_ID,
                    "PracticeGround",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply { description = "PracticeGround" }
                notificationManager!!.createNotificationChannel(notificationChannel)
            }
        }
        return notificationManager!!
    }

    private fun showNotification(context: Context, text: String) {
        val clickIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
        NotificationCompat.Builder(context, BROADCAST_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_heart_filled_24)
            .setContentTitle("Broadcast")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(clickIntent)
            .build()
            .let { getNotificationManager(context).notify(BROADCAST_NOTIFICATION, it) }
    }
}