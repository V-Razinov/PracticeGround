package ru.practiceground.presentation.motionlayout

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.base.BaseViewModel

class VidsViewModel : BaseViewModel() {

    val sceneProgress = MutableLiveData<Float>()
    val showFragment = SingleLiveEvent<BaseFragment>()
    val transitionListener = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) = Unit
        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) = Unit
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) = Unit

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            sceneProgress.value = p3
        }
    }
    private val fragment by lazy { Screens.motionLayout }

    fun onImageClick(view: View) {
        showFragment.value = fragment
    }
}