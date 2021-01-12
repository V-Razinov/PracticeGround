package ru.practiceground.presentation.motionlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.practiceground.App
import ru.practiceground.databinding.FragmentVidsBinding
import ru.practiceground.presentation.base.BaseFragment

class VidsFragment : BaseFragment() {

    override val viewModel: VidsViewModel by activityViewModels()
    private lateinit var binding: FragmentVidsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVidsBinding.inflate(inflater, container, false).apply {
            vidsMotion.transitionToEnd()
            cat1.setOnClickListener { viewModel.onImageClick() }
            cat2.setOnClickListener { viewModel.onImageClick() }
            cat3.setOnClickListener { viewModel.onImageClick() }
            cat4.setOnClickListener { viewModel.onImageClick() }
            cat5.setOnClickListener { viewModel.onImageClick() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            sceneProgress.observe(viewLifecycleOwner) { progress ->
                binding.vidsMotion.progress = progress
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
    }
}