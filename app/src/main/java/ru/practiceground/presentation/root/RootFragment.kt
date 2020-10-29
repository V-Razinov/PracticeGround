package ru.practiceground.presentation.root

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.RootFragmentBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class RootFragment : BaseFragment() {

    override val viewModel: RootViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private val rootAdapter: RootAdapter by lazy { RootAdapter().apply { setItemClickAction(viewModel::onItemClick) } }
    private lateinit var binding: RootFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.root_fragment)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rootRv.apply {
            adapter = rootAdapter
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.apply {
            items.setObserver(rootAdapter::setItems)
        }
    }
}