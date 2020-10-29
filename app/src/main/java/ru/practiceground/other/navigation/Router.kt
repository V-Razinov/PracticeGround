package ru.practiceground.other.navigation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.*
import ru.practiceground.R
import ru.practiceground.presentation.base.BaseFragment

class Router {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var finishActivity: () -> Unit
    private var containerId: Int = -1
    private val animPairs = listOf(
        R.anim.fragment_spin_enter to R.anim.fragment_spin_exit,
        R.anim.fragment_open_enter to R.anim.fragment_close_exit,
        R.anim.fragment_slide_from_left to R.anim.fragment_slide_to_left,
        R.anim.fragment_slide_from_right to R.anim.fragment_slide_to_right
    )

    private var onBackPressedCallback: (() -> Unit)? = null

    fun init(fragmentManager: FragmentManager, containerId: Int, finishActivity: () -> Unit) {
        this.fragmentManager = fragmentManager
        this.finishActivity = finishActivity
        this.containerId = containerId
    }

    fun navigateTo(fragment: BaseFragment) {
        val animPair = animPairs.random()
        fragmentManager.beginTransaction()
            .setCustomAnimations(animPair.first, 0, 0, animPair.second)
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        onBackPressedCallback?.let {
            it.invoke()
            return
        }

        if (fragmentManager.backStackEntryCount == 1)
            finishActivity.invoke()
        else
            fragmentManager.popBackStack()
    }

    fun setOnBackPressedCallback(action: () -> Unit) {
        onBackPressedCallback = action
    }

    fun removeOnBackPressedCallback() {
        onBackPressedCallback = null
    }

    fun newRootScreen(fragment: BaseFragment) {
        repeat(fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStackImmediate()
        }
        navigateTo(fragment)
    }

    fun addFragment(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fragment_open_enter, 0, 0, R.anim.fragment_close_exit)
            .add(containerId, fragment, null)
            .addToBackStack(null)
            .commit()
    }
}