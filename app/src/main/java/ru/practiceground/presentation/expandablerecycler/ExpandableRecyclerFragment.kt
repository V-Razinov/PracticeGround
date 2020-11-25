package ru.practiceground.presentation.expandablerecycler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.databinding.FragmentExpanableRecViewBinding
import ru.practiceground.presentation.base.BaseFragment

class ExpandableRecyclerFragment : BaseFragment() {

    override val viewModel: ExpandableRecyclerViewModel by viewModels()

    private lateinit var binding: FragmentExpanableRecViewBinding
    private val adapter: Adapter = Adapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExpanableRecViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.scrollToItem = binding.expRecRv::scrollToPosition

        binding.expRecRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ExpandableRecyclerFragment.adapter
        }

        viewModel.items.observe(viewLifecycleOwner, adapter::items::set)
    }
}