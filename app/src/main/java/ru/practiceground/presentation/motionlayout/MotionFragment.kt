package ru.practiceground.presentation.motionlayout

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.practiceground.R
import ru.practiceground.databinding.FragmentMotionBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

class MotionFragment : BaseFragment() {

    override val viewModel: VidsViewModel by activityViewModels()
    override val bgDrawable: Drawable? = null
    private lateinit var binding: FragmentMotionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_motion)
        binding.lifecycleOwner = this
        binding.motion.addTransitionListener1(viewModel.getProgressListener())
        binding.motion.transitionToStart()
        return binding.root
    }

    override fun onDestroyView() {
        viewModel.sceneProgress.value = 1f
        super.onDestroyView()
    }
}