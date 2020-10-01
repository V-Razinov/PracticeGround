package ru.practiceground.presentation.motionlayout

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.MutableLiveData
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.presentation.base.BaseViewModel

class VidsViewModel : BaseViewModel() {

    val sceneProgress = MutableLiveData<Float>()
    val showFragment = SingleLiveEvent<Boolean>()

    fun onImageClick(view: View) {
        showFragment.value = true
    }

    fun getProgressListener() = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = Unit
        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) = Unit
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            sceneProgress.value = p3
        }
    }
}