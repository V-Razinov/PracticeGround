package ru.practiceground.presentation.motionlayout

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.graphics.rotationMatrix
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.FragmentVidsBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.navigation.Screens
import ru.practiceground.presentation.base.BaseFragment
import kotlin.math.abs
import kotlin.math.absoluteValue

class VidsFragment : BaseFragment() {

    override val viewModel: VidsViewModel by activityViewModels()
    override val bgDrawable: Drawable? = null
    private val fragment by lazy { Screens.motionLayout }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentVidsBinding = getBinding(inflater, container, R.layout.fragment_vids)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.vidsMotion.transitionToEnd()
        viewModel.sceneProgress.observe(viewLifecycleOwner) {
            binding.vidsMotion.progress = abs(it)
        }
        viewModel.showFragment.observe(viewLifecycleOwner) {
            childFragmentManager.beginTransaction()
                .replace(binding.vidsContainer.id, fragment)
                .runOnCommit {
                    val router = App.router
                    router.setOnBackPressedCallback {
                        childFragmentManager.popBackStack()
                        router.removeOnBackPressedCallback()
                    }
                }
                .commit()
        }
        return binding.root
    }
}