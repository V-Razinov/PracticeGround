package ru.practiceground.presentation.root

import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment

class RootItem(
    val type: RootItemTypes,
    val title: String = type.title
)

enum class RootItemTypes(val title: String, val getFragment: () -> BaseFragment) {
    DISCORD_VIEW_PAGER("Farewell's discord", Screens::discord::get),
    EXPANDABLE_RECYCLER("Expandable Recycler", Screens::expandableRecView::get),
    ALL_IN_ONE("All in One rec view", Screens::allInOneRecVIew::get),
    FINGERPRINT("FingerPrint", Screens::fingerprint::get),
    DRAWER("Drawer", Screens::drawer::get)
}