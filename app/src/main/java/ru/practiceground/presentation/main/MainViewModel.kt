package ru.practiceground.presentation.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import ru.practiceground.App
import ru.practiceground.other.ShowLoader
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.navigation.Screens

class MainViewModel : ViewModel() {

    val showLoader = SingleLiveEvent<Boolean>()

    fun onCreate(supportFragmentManager: FragmentManager, container: Int, finishActivity: () -> Unit) {
        App.router.init(supportFragmentManager, container, finishActivity)
        App.router.navigateTo(Screens.mainScreen)
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun showLoader(event: ShowLoader) {
        showLoader.value = event.show
    }

    fun onBackPressed() {
        App.router.back()
    }

    fun onDestroy() {
        EventBus.getDefault().unregister(this)
    }
}