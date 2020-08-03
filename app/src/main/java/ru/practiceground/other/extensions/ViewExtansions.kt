package ru.practiceground.other.extensions

import android.animation.Animator
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.setListener(
    onStart: (animator: Animator?) -> Unit = { },
    onEnd: (animator: Animator?) -> Unit = { },
    onCancel: (animator: Animator?) -> Unit = { },
    onRepeat: (animator: Animator?) -> Unit = { }
): ViewPropertyAnimator = this.setListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) = onStart.invoke(animation)
    override fun onAnimationEnd(animation: Animator?) = onEnd.invoke(animation)
    override fun onAnimationCancel(animation: Animator?) = onCancel.invoke(animation)
    override fun onAnimationRepeat(animation: Animator?) = onRepeat.invoke(animation)
})
