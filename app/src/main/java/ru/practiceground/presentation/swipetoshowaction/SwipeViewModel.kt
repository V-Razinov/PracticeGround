package ru.practiceground.presentation.swipetoshowaction

import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.customviews.DragToShowActionsLayout
import ru.practiceground.presentation.base.BaseRecViewItem
import ru.practiceground.presentation.base.BaseViewModel

class SwipeViewModel : BaseViewModel() {

    val items = MutableLiveData<List<BaseRecViewItem>>(
        listOf(
            SwipeItem(DragToShowActionsLayout.Panels.START),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem(),
            SwipeItem()
        )
    )
}