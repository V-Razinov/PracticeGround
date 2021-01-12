package ru.practiceground

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.practiceground.domain.di.RoomModule
import ru.practiceground.other.navigation.Router
import ru.practiceground.presentation.roomlivedata.RoomLiveDataSubComponentComponent

class App : Application(), AppComponent {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        router = Router()
        appComponent = DaggerAppComponent.builder().build()
    }

    companion object {
        lateinit var context: Context
        lateinit var router: Router
        lateinit var appComponent: AppComponent
    }
    override fun viewPagerComponent(): RoomLiveDataSubComponentComponent {
        return appComponent.viewPagerComponent()
    }
}