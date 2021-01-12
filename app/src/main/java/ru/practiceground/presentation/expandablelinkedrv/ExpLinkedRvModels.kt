package ru.practiceground.presentation.expandablelinkedrv

abstract class BaseExpLinkedItem(val viewType: Int) {
    companion object {
        const val VIEW_TYPE_TITLE = 0
        const val VIEW_TYPE_BODY = 1
    }
}

data class ExpLinkedItem(
    val title: String,
    val items: List<ExpLinkedSubItem>,
    var isExpanded: Boolean = false
) : BaseExpLinkedItem(VIEW_TYPE_TITLE)

data class ExpLinkedSubItem(
    val title: String,
    val body: String,
    var state: CollapsedState = CollapsedState.COLLAPSED
) : BaseExpLinkedItem(VIEW_TYPE_BODY)

enum class CollapsedState { COLLAPSED, EXPANDED }