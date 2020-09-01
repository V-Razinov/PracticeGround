package ru.practiceground.presentation.root

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.root.RootItemTypes.*

class RootViewModel : BaseViewModel() {

    val items = MutableLiveData<List<RootItem>>()

    override fun onViewCreated() {
        super.onViewCreated()
        items.value = RootItemTypes.values().map { RootItem(it) }
    }

    fun onItemClick(item: RootItemTypes) {
        router.navigateTo(item.getFragment())
    }
}