package ru.practiceground.presentation.vk.pages.hub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.databinding.FragmentHubBinding
import ru.practiceground.presentation.base.BaseFragment

class HubFragment : BaseFragment() {

    override val viewModel: HubViewModel by viewModels()

    private lateinit var binding: FragmentHubBinding
    private val adapter = HubAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHubBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.apply {
            adapter = this@HubFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        subscribe()
    }

    private fun subscribe() {
        with(viewModel) {
            items.observe(adapter::setItems)
            notifyItemChanged.observe(adapter::notifyByViewType)
        }
    }
}