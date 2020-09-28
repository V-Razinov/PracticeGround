package ru.practiceground.presentation.vk

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.practiceground.presentation.base.BaseFragment

class VKViewPagerAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment) {

    private var pages: List<BaseFragment> = emptyList()

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]

    fun setPages(newPage: List<BaseFragment>) {
        pages = newPage
        notifyDataSetChanged()
    }
}