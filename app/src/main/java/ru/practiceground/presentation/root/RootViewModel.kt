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
            RootItem(ALL_IN_ONE)
        )
    }

    fun onItemClick(type: RootItemTypes) {
        when (type) {
            DISCORD_VIEW_PAGER -> router.navigateTo(Screens.discord)
            EXPANDABLE_RECYCLER -> router.navigateTo(Screens.expandableRecView)
            ALL_IN_ONE -> router.navigateTo(Screens.allInOneRecVIew)
        }
    }
}