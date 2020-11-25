package ru.practiceground.other.extensions

import android.animation.Animator
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.ViewPropertyAnimator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView

inline fun ViewPropertyAnimator.setListener(
    crossinline onStart: (animator: Animator?) -> Unit = { },
    crossinline onEnd: (animator: Animator?) -> Unit = { },
    crossinline onCancel: (animator: Animator?) -> Unit = { },
    crossinline onRepeat: (animator: Animator?) -> Unit = { }
): ViewPropertyAnimator = setListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) = onStart.invoke(animation)
    override fun onAnimationEnd(animation: Animator?) = onEnd.invoke(animation)
    override fun onAnimationCancel(animation: Animator?) = onCancel.invoke(animation)
    override fun onAnimationRepeat(animation: Animator?) = onRepeat.invoke(animation)
})

fun NavigationView.setOnMenuItemClickListener(action: (item: MenuItem) -> Boolean) {
    setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener(action))
}

val ViewBinding.context: Context get() = root.context

fun ViewPager2.disableOverScroll() {
    getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
}

fun TextView.setCompoundDrawableStart(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(
    drawable,
    compoundDrawablesRelative.elementAtOrNull(1) ?: compoundDrawables.elementAtOrNull(1),
    compoundDrawablesRelative.elementAtOrNull(2) ?: compoundDrawables.elementAtOrNull(2),
    compoundDrawablesRelative.elementAtOrNull(3) ?: compoundDrawables.elementAtOrNull(3)
)
fun TextView.setCompoundDrawableTop(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(
    compoundDrawablesRelative.elementAtOrNull(0) ?: compoundDrawables.elementAtOrNull(0),
    drawable,
    compoundDrawablesRelative.elementAtOrNull(2) ?: compoundDrawables.elementAtOrNull(2),
    compoundDrawablesRelative.elementAtOrNull(3) ?: compoundDrawables.elementAtOrNull(3)
)
fun TextView.setCompoundDrawableEnd(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(
    compoundDrawablesRelative.elementAtOrNull(0) ?: compoundDrawables.elementAtOrNull(0),
    compoundDrawablesRelative.elementAtOrNull(1) ?: compoundDrawables.elementAtOrNull(1),
    drawable,
    compoundDrawablesRelative.elementAtOrNull(3) ?: compoundDrawables.elementAtOrNull(3)
)
fun TextView.setCompoundDrawableBottom(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(
    compoundDrawablesRelative.elementAtOrNull(0) ?: compoundDrawables.elementAtOrNull(0),
    compoundDrawablesRelative.elementAtOrNull(1) ?: compoundDrawables.elementAtOrNull(1),
    compoundDrawablesRelative.elementAtOrNull(2) ?: compoundDrawables.elementAtOrNull(2),
    drawable
)

fun TextView.replaceCompoundDrawablesStart(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
fun TextView.replaceCompoundDrawablesTop(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
fun TextView.replaceCompoundDrawablesEnd(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
fun TextView.replaceCompoundDrawablesBottom(drawable: Drawable?) = setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable)