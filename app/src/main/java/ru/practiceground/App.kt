package ru.practiceground

import android.app.Application
import android.content.Context
import ru.practiceground.other.navigation.Router

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        router = Router()
    }

    companion object {
        lateinit var instance: App
        lateinit var context: Context
        lateinit var router: Router
    }
}