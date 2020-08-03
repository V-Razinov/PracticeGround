package ru.practiceground.presentation.allinonerecview

import androidx.annotation.DrawableRes
import ru.practiceground.other.views.DragToShowActionsLayout.*
import ru.practiceground.presentation.base.BaseRecViewItem

class ExpandableItem(
    val title: String,
    val body: String,
    var isExpanded: Boolean = false
) : BaseRecViewItem()

class HorizontalRecViewItem(
    val items: List<GifItem>,
    var isExpanded: Boolean = false
) : BaseRecViewItem()

class GifItem(
    @DrawableRes val id: Int
) : BaseRecViewItem()

class SwipeItem(
    var currentPanel: Panels = Panels.CENTER
) : BaseRecViewItem()