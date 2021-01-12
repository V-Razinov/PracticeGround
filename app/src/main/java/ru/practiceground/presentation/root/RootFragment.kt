package ru.practiceground.presentation.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.practiceground.databinding.RootFragmentBinding
import ru.practiceground.presentation.base.BaseFragment
import kotlin.LazyThreadSafetyMode.NONE

class RootFragment : BaseFragment() {

    override val viewModel: RootViewModel by viewModels()

    private val rootAdapter by lazy(NONE) { RootAdapter().apply { setItemClickAction(viewModel::onItemClick) } }

    private lateinit var binding: RootFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RootFragmentBinding.inflate(inflater, container, false)
        binding.rootRv.apply {
            adapter = rootAdapter
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        viewModel.apply {
            items.observe(rootAdapter::setItems)
        }
    }
}