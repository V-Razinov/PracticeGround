package ru.practiceground.presentation.roomlivedata

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.practiceground.presentation.roomlivedata.pages.all.PageAllFragment
import ru.practiceground.presentation.roomlivedata.pages.favs.PageFavsFragment

class ViewPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {
    //xD
    private val fragments = listOf(
        lazy(LazyThreadSafetyMode.NONE, ::PageAllFragment),
        lazy(LazyThreadSafetyMode.NONE, ::PageFavsFragment)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position].value

    fun callResume(position: Int) {
        try {
            fragments[position].value.let { frg ->
                (frg as? PageAllFragment)?.setCounter() ?: (frg as? PageFavsFragment)?.setCounter()
            }
        } catch (e: Exception) { /*ignore*/ }
    }
}