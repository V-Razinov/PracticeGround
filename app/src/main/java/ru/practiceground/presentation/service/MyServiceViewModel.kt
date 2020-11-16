package ru.practiceground.presentation.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.Message
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.service.MyBoundService.Companion.BOUND_NOTIFICATION

class MyServiceViewModel : BaseViewModel() {

    val message = MutableLiveData("Message")
    private var bounded = false
    private val connection: ServiceConnection
    private lateinit var service: MyBoundService

    init {
        connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                this@MyServiceViewModel.service = (service as MyBoundService.MyBinder).service
                bounded = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                bounded = false
            }
        }
    }

    fun createBoundService(acty: Activity) {
        Intent(acty, MyBoundService::class.java).also {
            acty.bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun stopBoundService(acty: Activity) {
        acty.unbindService(connection)
    }

    fun sendMessage(context: Context) {
        //Через start service
        context.startService(Intent(context, MyService::class.java).apply {
            putExtra(MyService.NOTIF_TITLE, "MyServiceTitle")
            putExtra(MyService.NOTIF_CONTENT, message.value)
        })

        //Через bound service
        if (bounded) {
            Message.obtain().apply {
                what = BOUND_NOTIFICATION
                obj = message.value
            }.also(service::send)
        }

        //Через broadcast
        Intent().apply {
            action = MyBroadcastReceiver.RECEIVER_ACTION
            putExtra(MyService.NOTIF_CONTENT, message.value)
        }.also(LocalBroadcastManager.getInstance(context)::sendBroadcast)
    }
}