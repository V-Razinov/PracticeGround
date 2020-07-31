package ru.practiceground.presentation.root

class RootItem(
    val type: RootItemTypes,
    val title: String = type.title
)

enum class RootItemTypes(val title: String) {
    DISCORD_VIEW_PAGER("Farewell's discord"),
    EXPANDABLE_RECYCLER("Expandable Recycler"),
    ALL_IN_ONE("All in One rec view")
}