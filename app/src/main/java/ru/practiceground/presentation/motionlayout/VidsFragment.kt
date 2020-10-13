package ru.practiceground.presentation.motionlayout

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.FragmentVidsBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.base.BaseViewModel
import kotlin.math.abs

class VidsFragment : BaseFragment() {

    override val viewModel: VidsViewModel by activityViewModels()
    override val bgDrawable: Drawable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentVidsBinding = getBinding(inflater, container, R.layout.fragment_vids)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        binding.vidsMotion.transitionToEnd()
        viewModel.subscribe {
            sceneProgress.observe(viewLifecycleOwner) { progress ->
                binding.vidsMotion.progress = abs(progress)
            }
            showFragment.observe(viewLifecycleOwner) { fragment ->
                binding.vidsMotion.transitionToStart()

                childFragmentManager.beginTransaction()
                    .replace(binding.vidsContainer.id, fragment)
                    .addToBackStack(null)
                    .commit()

                val router = App.router
                router.setOnBackPressedCallback {
                    childFragmentManager.popBackStack()
                    router.removeOnBackPressedCallback()
                }
            }
        }
        return binding.root
    }

    private fun <VM : BaseViewModel> VM.subscribe(action: VM.() -> Unit) = action(this)
}