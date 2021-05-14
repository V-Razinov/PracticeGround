package ru.practiceground.presentation.root

import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment

class RootItem(
    val type: RootItems,
    val title: String = type.title
)

enum class RootItems(val title: String, val getFragment: () -> BaseFragment) {
    DISCORD_VIEW_PAGER("Farewell's discord", Screens::discord),
    EXPANDABLE_RECYCLER("Expandable Recycler", Screens::expandableRecView),
    EXPANDABLE_LINKED_RECYCLER("Expandable Linked Recycler", Screens::expLinkedRv),
    ALL_IN_ONE("Swipe To Show Action", Screens::swipeToShowAction),
    FINGERPRINT("FingerPrint", Screens::fingerprint),
    DRAWER("Drawer", Screens::drawer),
    VIEWPAGER("ViewPager2 + Room + liveData", Screens::viewPager),
    CUSTOM_MESSAGES("Custom messages(Toast + SnackBar)", Screens::customMessages),
    FILE_PICKER("File picker and compressor", Screens::filePicker),
    CUSTOM_VIEWS("VK", Screens::vk),
    MOTION_LAYOUT("Motion layout", Screens::vidsFragment),
    MY_SERVICE("Notif service", Screens::myService),
    DOBULE_VP("double view pager", Screens::doubleVp),
    MOTION2("motion2", Screens::motion2)
}