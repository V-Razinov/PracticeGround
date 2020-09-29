package ru.practiceground.presentation.expandablerecycler

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class ExpandableRecyclerViewModel : BaseViewModel() {

    val items = MutableLiveData<List<MyItem>>()

    init {
        items.value = listOf(MyItem(), MyItem(), MyItem(), MyItem(), MyItem())

    }
}