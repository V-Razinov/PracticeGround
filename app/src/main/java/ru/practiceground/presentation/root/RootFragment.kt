package ru.practiceground.presentation.root

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.root_fragment.*
import ru.practiceground.R
import ru.practiceground.databinding.RootFragmentBinding
import ru.practiceground.other.getBinding
import ru.practiceground.other.getColor
import ru.practiceground.presentation.base.BaseFragment

class RootFragment : BaseFragment() {

    override val viewModel: RootViewModel by viewModels()
    override val bgDrawable: Drawable? = ColorDrawable(getColor(R.color.whiteFFF))

    private val rootAdapter = RootAdapter()
    private lateinit var binding: RootFragmentBinding
    private lateinit var lm: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.root_fragment)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lm = GridLayoutManager(context, 3)

        binding.rootRv.apply {
            adapter = rootAdapter.apply { setItemClickAction(viewModel::onItemClick) }
            layoutManager = lm
        }

        subscribe()
    }

    fun subscribe() {
        viewModel.apply {
            items.setObserver(rootAdapter::setItems)
        }
    }
}