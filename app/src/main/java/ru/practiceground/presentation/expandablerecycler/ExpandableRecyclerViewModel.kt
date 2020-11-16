package ru.practiceground.presentation.expandablerecycler

import androidx.lifecycle.MutableLiveData
import ru.practiceground.presentation.base.BaseViewModel

class ExpandableRecyclerViewModel : BaseViewModel() {

    val items = MutableLiveData(listOf(MyItem(), MyItem(), MyItem(), MyItem(), MyItem()))

}