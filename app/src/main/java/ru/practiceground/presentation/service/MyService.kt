package ru.practiceground.presentation.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import ru.practiceground.R
import ru.practiceground.presentation.main.MainActivity

class MyService : Service() {
    companion object {
        const val NOTIF_TITLE = "0"
        const val NOTIF_CONTENT = "1"
        const val SERVICE_NOTIFICATION = 0
        const val SERVICE_CHANNEL_ID = "123123123"
    }

    private lateinit var looper: Looper
    private lateinit var handler: MyHandler
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        looper = HandlerThread("MyService" , Process.THREAD_PRIORITY_BACKGROUND).run { start(); looper }
        handler = MyHandler(this, notificationManager, looper)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                SERVICE_CHANNEL_ID,
                "PracticeGround",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "PracticeGround" }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int = intent?.let { intent ->
        val text = intent.extras?.getString(NOTIF_CONTENT) ?: return@let START_STICKY

        handler.obtainMessage(SERVICE_NOTIFICATION, text).let { msg ->
            handler.sendMessage(msg)
        }

        START_STICKY
    } ?: super.onStartCommand(intent, flags, startId)

    override fun onBind(intent: Intent?): IBinder? = null

    private class MyHandler(
        private val context: Context,
        private val notificationManager: NotificationManager,
        looper: Looper
    ) : Handler(looper) {

        @Suppress("UNCHECKED_CAST")
        override fun handleMessage(msg: Message) {
            if (msg.what == SERVICE_NOTIFICATION) {
                showNotification(msg.obj as? String ?: return)
            }
        }

        private fun showNotification(text: String) {
            val clickIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
            NotificationCompat.Builder(context, SERVICE_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_heart_filled_24)
                .setContentTitle("StartService")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(clickIntent)
                .build()
                .let { notificationManager.notify(SERVICE_NOTIFICATION, it) }
        }
    }
}