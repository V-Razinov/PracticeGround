package ru.practiceground.presentation.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import ru.practiceground.App
import ru.practiceground.other.navigation.Screens

class MainViewModel : ViewModel() {

    fun onCreate(supportFragmentManager: FragmentManager, container: Int, finishActivity: () -> Unit) {
        App.router.init(supportFragmentManager, container, finishActivity)
        App.router.navigateTo(Screens.mainScreen)
    }

    fun onBackPressed() {
        App.router.back()
    }
}