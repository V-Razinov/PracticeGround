package ru.practiceground.presentation.roomlivedata.pages.favs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentPageFavsBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.roomlivedata.pages.PageAdapter

class PageFavsFragment : BaseFragment() {

    override val viewModel: PageFavsViewModel by viewModels()
    override val bgDrawable: Drawable? = defaultBgColor
    private lateinit var binding: FragmentPageFavsBinding
    private val adapter = PageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_page_favs)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.apply {
            layoutManager = LinearLayoutManager(context ?: return)
            adapter = this@PageFavsFragment.adapter
        }

        viewModel.items.setObserver { (items, callback) ->
            adapter.setItems(items, callback)
            binding.image.isVisible = items.isEmpty()
        }
        viewModel.clickHandler.setObserver(adapter::setClickHandler)
    }
}