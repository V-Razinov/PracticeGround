package ru.practiceground.other.navigation

import androidx.fragment.app.FragmentManager
import ru.practiceground.presentation.base.BaseFragment

class Router {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var finishActivity: () -> Unit
    private var containerId: Int = -1

    private val size get() = fragmentManager.fragments.size
    private val fragments get() = fragmentManager.fragments

    fun init(fragmentManager: FragmentManager, containerId: Int, finishActivity: () -> Unit) {
        this.fragmentManager = fragmentManager
        this.finishActivity = finishActivity
        this.containerId = containerId
    }

    fun navigateTo(fragment: BaseFragment) {
        fragmentManager.apply {
            if (size > 0) hideCurrentFragment()
            beginTransaction()
                .add(containerId, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun back() {
        if (size == 1) {
            finishActivity.invoke()
        } else {
            fragmentManager.apply {
                popBackStack()
                showLastFragment()
            }
        }
    }

    fun newRootScreen(fragment: BaseFragment) {
        repeat(size) {
            fragmentManager.popBackStackImmediate()
        }
        navigateTo(fragment)
    }

    fun replace(fragment: BaseFragment) {
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun FragmentManager.hideCurrentFragment() {
        executePendingTransactions()
        fragments.last().let { beginTransaction().hide(it).commit() }
    }

    private fun FragmentManager.showLastFragment() {
        executePendingTransactions()
        fragments.last().let { beginTransaction().show(it).commit() }
    }
}