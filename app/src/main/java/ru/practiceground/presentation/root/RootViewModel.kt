package ru.practiceground.presentation.root

import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseViewModel
import ru.practiceground.presentation.root.RootItemTypes.*

class RootViewModel : BaseViewModel() {

    val items = MutableLiveData<List<RootItem>>()

    override fun onViewCreated() {
        super.onViewCreated()
        items.value = listOf(
            RootItem(DISCORD_VIEW_PAGER),
            RootItem(EXPANDABLE_RECYCLER),
            RootItem(ALL_IN_ONE),
            RootItem(FINGERPRINT)
        )
    }

    fun onItemClick(type: RootItemTypes) {
        router.navigateTo(type.getFragment.invoke())
    }
}