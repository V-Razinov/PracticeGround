package ru.practiceground.other.extensions

import android.animation.Animator
import android.view.MenuItem
import android.view.ViewPropertyAnimator
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

inline fun ViewPropertyAnimator.setListener(
    crossinline onStart: (animator: Animator?) -> Unit = { },
    crossinline onEnd: (animator: Animator?) -> Unit = { },
    crossinline onCancel: (animator: Animator?) -> Unit = { },
    crossinline onRepeat: (animator: Animator?) -> Unit = { }
): ViewPropertyAnimator = this.setListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) = onStart.invoke(animation)
    override fun onAnimationEnd(animation: Animator?) = onEnd.invoke(animation)
    override fun onAnimationCancel(animation: Animator?) = onCancel.invoke(animation)
    override fun onAnimationRepeat(animation: Animator?) = onRepeat.invoke(animation)
})

fun NavigationView.setOnMenuItemClickListener(action: (item: MenuItem) -> Boolean) {
    setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener(action))
}
