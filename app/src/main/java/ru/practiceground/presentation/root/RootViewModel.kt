package ru.practiceground.presentation.root

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class RootViewModel : BaseViewModel() {

    val items = MutableLiveData(RootItems.values().map { RootItem(it) })

    fun onItemClick(item: RootItems) {
        router.navigateTo(item.getFragment())
    }
}