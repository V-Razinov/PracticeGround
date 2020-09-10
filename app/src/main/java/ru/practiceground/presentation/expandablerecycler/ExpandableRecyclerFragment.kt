package ru.practiceground.presentation.expandablerecycler

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentExpanableRecViewBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class ExpandableRecyclerFragment : BaseFragment() {

    override val viewModel: ExpandableRecyclerViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private lateinit var binding: FragmentExpanableRecViewBinding
    private val adapter = Adapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_expanable_rec_view)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.scrollToItem = binding.expRecRv::scrollToPosition

        binding.expRecRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ExpandableRecyclerFragment.adapter
        }

        viewModel.items.setObserver(adapter::items::set)
    }
}