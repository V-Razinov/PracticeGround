package ru.practiceground.presentation.expandablelinkedrv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import ru.practiceground.databinding.FragmentExpLinkedRvBinding
import ru.practiceground.presentation.base.BaseFragment

class ExpLinkedRvFragment : BaseFragment() {

    override val viewModel: ExpLinkedRvViewModel by viewModels()

    private lateinit var binding: FragmentExpLinkedRvBinding
    private val adapter = ExpLinkedRvAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExpLinkedRvBinding.inflate(layoutInflater, container, false).apply {
            recView.apply {
                adapter = this@ExpLinkedRvFragment.adapter
                layoutManager = LinearLayoutManager(context)
                itemAnimator = ExpLinkItemAnimator()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.items.observe(adapter::setItems)
    }
}