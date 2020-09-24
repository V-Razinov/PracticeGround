package ru.practiceground.presentation.vk.pages.hub

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentHubBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

class HubFragment : BaseFragment() {

    override val viewModel: HubViewModel by viewModels()
    override val bgDrawable: Drawable? = null

    private lateinit var binding: FragmentHubBinding
    private val adapter = HubAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_hub)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.apply {
            adapter = this@HubFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.items.setObserver(adapter::setItems)
    }
}