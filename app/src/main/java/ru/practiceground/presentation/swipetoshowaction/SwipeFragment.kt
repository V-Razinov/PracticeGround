package ru.practiceground.presentation.swipetoshowaction

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentSwipeToShowActionsBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class SwipeFragment : BaseFragment() {

    override val viewModel: SwipeViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private lateinit var binding: FragmentSwipeToShowActionsBinding
    private val adapter = SwipeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_swipe_to_show_actions)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SwipeFragment.adapter
        }

        viewModel.items.setObserver(adapter::items::set)
    }
}