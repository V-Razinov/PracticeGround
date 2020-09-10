package ru.practiceground.other.navigation

import ru.practiceground.presentation.custommessages.CustomMessagesFragment
import ru.practiceground.presentation.discord.DiscordFragment
import ru.practiceground.presentation.drawer.DrawerFragment
import ru.practiceground.presentation.swipetoshowaction.SwipeFragment
import ru.practiceground.presentation.expandablerecycler.ExpandableRecyclerFragment
import ru.practiceground.presentation.fingerprintcheck.FingerPrintFragment
import ru.practiceground.presentation.root.RootFragment
import ru.practiceground.presentation.roomlivedata.ViewPagerFragment

object Screens {

    val mainScreen get() = RootFragment()
    val discord get() = DiscordFragment()
    val expandableRecView get() = ExpandableRecyclerFragment()
    val swipeToShowAction get() = SwipeFragment()
    val fingerprint get() = FingerPrintFragment()
    val drawer get() = DrawerFragment()
    val viewPager get() = ViewPagerFragment()
    val customMessages get() = CustomMessagesFragment()
}