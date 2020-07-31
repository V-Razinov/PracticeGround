package ru.practiceground.presentation.expandablerecycler

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class ExpandableRecyclerViewModel : BaseViewModel() {

    val items = MutableLiveData<List<MyItem>>()

    override fun onResume() {
        super.onResume()
        items.value = listOf(MyItem(), MyItem(), MyItem(), MyItem(), MyItem())
        items.value
    }

    override fun onViewCreated() {
        super.onViewCreated()
        items.value = listOf(MyItem(), MyItem(), MyItem(), MyItem(), MyItem())
        items.value
    }
}