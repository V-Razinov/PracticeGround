package ru.practiceground.other.navigation

import androidx.fragment.app.FragmentManager
import ru.practiceground.presentation.base.BaseFragment

class Router {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var finishActivity: () -> Unit
    private var containerId: Int = -1

    private val size get() = fragmentManager.fragments.size

    fun init(fragmentManager: FragmentManager, containerId: Int, finishActivity: () -> Unit) {
        this.fragmentManager = fragmentManager
        this.finishActivity = finishActivity
        this.containerId = containerId
    }

    fun navigateTo(fragment: BaseFragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 1)
            finishActivity.invoke()
        else
            fragmentManager.popBackStack()
    }

    fun newRootScreen(fragment: BaseFragment) {
        repeat(fragmentManager.backStackEntryCount) {
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
}