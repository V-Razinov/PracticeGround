package ru.practiceground.presentation.root

class RootItem(
    val type: RootItemTypes,
    val title: String
)

enum class RootItemTypes {
    DISCORD_VIEW_PAGER
}