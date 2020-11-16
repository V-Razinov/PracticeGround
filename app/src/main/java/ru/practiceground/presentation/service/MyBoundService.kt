package ru.practiceground.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import ru.practiceground.R
import ru.practiceground.presentation.main.MainActivity

class MyBoundService : Service() {
    companion object {
        const val BOUND_CHANNEL_ID = "123123123"
        const val BOUND_NOTIFICATION = 2
    }

    private val binder = MyBinder()
    private lateinit var looper: Looper
    private lateinit var handler: BoundHandler
    private lateinit var notificationManager: NotificationManager

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        looper = HandlerThread("MyService" , Process.THREAD_PRIORITY_BACKGROUND).run { start(); looper }
        handler = BoundHandler(looper, this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                BOUND_CHANNEL_ID,
                "PracticeGround",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "PracticeGround" }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun send(msg: Message) = handler.sendMessage(msg)

    inner class MyBinder : Binder() {
        val service = this@MyBoundService
    }

    private inner class BoundHandler(looper: Looper, private val context: Context) : Handler(looper) {

        private val Message.WAT get() = what

        override fun handleMessage(msg: Message) {
            if (msg.WAT == BOUND_NOTIFICATION) {
                (msg.obj as? String)?.let { message ->
                    showNotification(message)
                }
            }
        }

        private fun showNotification(text: String) {
            val clickIntent = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), 0)
            NotificationCompat.Builder(context, BOUND_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_heart_filled_24)
                .setContentTitle("BoundService")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(clickIntent)
                .build()
                .let { notificationManager.notify(BOUND_NOTIFICATION, it) }
        }
    }
}