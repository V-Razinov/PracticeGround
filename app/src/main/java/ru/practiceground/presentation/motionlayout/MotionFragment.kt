package ru.practiceground.presentation.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.practiceground.databinding.FragmentMotionBinding
import ru.practiceground.presentation.base.BaseFragment

class MotionFragment : BaseFragment() {

    override val viewModel: VidsViewModel by activityViewModels()
    private lateinit var binding: FragmentMotionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMotionBinding.inflate(inflater, container, false).apply {
            motion.addTransitionListener1(viewModel.transitionListener)
            motion.transitionToStart()
        }
        return binding.root
    }

    override fun onDestroyView() {
        viewModel.sceneProgress.value = 1f
        super.onDestroyView()
    }
}