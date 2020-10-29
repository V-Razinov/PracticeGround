package ru.practiceground.presentation.root

import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment

class RootItem(
    val type: RootItems,
    val title: String = type.title
)

enum class RootItems(val title: String, val getFragment: () -> BaseFragment) {
    DISCORD_VIEW_PAGER("Farewell's discord", Screens::discord::get),
    EXPANDABLE_RECYCLER("Expandable Recycler", Screens::expandableRecView::get),
    ALL_IN_ONE("Swipe To Show Action", Screens::swipeToShowAction::get),
    FINGERPRINT("FingerPrint", Screens::fingerprint::get),
    DRAWER("Drawer", Screens::drawer::get),
    VIEWPAGER("ViewPager2 + Room + liveData", Screens::viewPager::get),
    CUSTOM_MESSAGES("Custom messages(Toast + SnackBar)", Screens::customMessages::get),
    FILE_PICKER("File picker", Screens::filePicker::get),
    CUSTOM_VIEWS("VK", Screens::vk::get),
    MOTION_LAYOUT("Motion layout", Screens::vidsFragment::get),
    MY_SERVICE("Notif service", Screens::myService::get)
}