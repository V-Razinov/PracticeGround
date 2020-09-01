package ru.practiceground.presentation.drawer

import android.view.MenuItem
import ru.practiceground.presentation.base.BaseViewModel

class DrawerViewModel : BaseViewModel() {

    fun onMenuClick(item: MenuItem): Boolean {
        showMessage(item.title.toString())
        return true
    }
}