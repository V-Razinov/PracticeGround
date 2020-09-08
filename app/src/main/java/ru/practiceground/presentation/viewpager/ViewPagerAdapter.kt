package ru.practiceground.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.practiceground.presentation.viewpager.pages.all.PageAllFragment
import ru.practiceground.presentation.viewpager.pages.favs.PageFavsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(PageAllFragment(), PageFavsFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}