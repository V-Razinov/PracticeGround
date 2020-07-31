package ru.practiceground.presentation.allinonerecview

import androidx.lifecycle.MutableLiveData
import ru.practiceground.R
import ru.practiceground.presentation.base.BaseRecViewItem
import ru.practiceground.presentation.base.BaseViewModel

class AllInOneRecViewViewModel : BaseViewModel() {

    val items = MutableLiveData<List<BaseRecViewItem>>()

    override fun onViewCreated() {
        super.onViewCreated()
        items.value = listOf(
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            HorizontalRecViewItem(
                listOf(
                    GifItem(R.drawable.rick_dance),
                    GifItem(R.drawable.aniki_flip),
                    GifItem(R.drawable.aniki1),
                    GifItem(R.drawable.kepp_speak)
                )
            ),
            SwipeItem(),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator"),
            ExpandableItem("enable memory view", "Appearance & Behavior -> Appearance-> Window options -> enable show Memory indicator")
        )
    }
}