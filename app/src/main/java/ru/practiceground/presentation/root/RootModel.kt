package ru.practiceground.presentation.root

import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment

class RootItem(
    val type: RootItemTypes,
    val title: String = type.title
)

enum class RootItemTypes(val title: String, val getFragment: () -> BaseFragment) {
    DISCORD_VIEW_PAGER("Farewell's discord", Screens::fingerprint::get),
    EXPANDABLE_RECYCLER("Expandable Recycler", Screens::fingerprint::get),
    ALL_IN_ONE("All in One rec view", Screens::fingerprint::get),
    FINGERPRINT("FingerPrint", Screens::fingerprint::get)
}