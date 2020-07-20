package ru.practiceground.presentation.root

import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseViewModel

class RootViewModel : BaseViewModel() {

    val items = MutableLiveData<List<RootItem>>()

    override fun onViewCreated() {
        super.onViewCreated()
        items.value = listOf(
            RootItem(RootItemTypes.DISCORD_VIEW_PAGER, "Farewell's discord")
        )
    }

    fun onItemClick(type: RootItemTypes){
        when(type) {
            RootItemTypes.DISCORD_VIEW_PAGER -> router.navigateTo(Screens.discord)
        }
    }
}