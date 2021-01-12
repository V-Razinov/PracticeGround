package ru.practiceground.presentation.swipetoshowaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.databinding.FragmentSwipeToShowActionsBinding
import ru.practiceground.presentation.base.BaseFragment

class SwipeFragment : BaseFragment() {

    override val viewModel: SwipeViewModel by viewModels()

    private lateinit var binding: FragmentSwipeToShowActionsBinding
    private val adapter = SwipeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSwipeToShowActionsBinding.inflate(inflater, container, false).apply {
            recView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SwipeFragment.adapter
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.items.observe(adapter::items::set)
    }
}