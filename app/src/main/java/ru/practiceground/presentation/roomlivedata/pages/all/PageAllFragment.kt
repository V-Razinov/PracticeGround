package ru.practiceground.presentation.roomlivedata.pages.all

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_view_pager.*
import ru.practiceground.R
import ru.practiceground.databinding.FragmentPageAllBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.roomlivedata.pages.PagePagingAdapter

class PageAllFragment : BaseFragment() {

    override val viewModel: PageAllViewModel by viewModels()
    override val bgDrawable: Drawable? = defaultBgColor
    private lateinit var binding: FragmentPageAllBinding
    private val adapter = PagePagingAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_page_all)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            adapter.setOnItemsCountChangedAction { itemCount ->
                itemsCount.text = itemCount.toString()
            }
            itemsCountCard.setOnClickListener {
                Toast.makeText(context, "Всего элементов в адаптере", Toast.LENGTH_LONG).show()
            }
            recView.apply {
                layoutManager = LinearLayoutManager(context ?: return)
                adapter = this@PageAllFragment.adapter
                post { updatePadding(bottom = parentFragment?.fab?.run { marginBottom + height } ?: paddingBottom) }
            }
        }
        viewModel.items.setObserver {
            adapter.submitList(it)
            binding.image.isVisible = it.isEmpty()
        }
        viewModel.clickHandler.setObserver(adapter::setClickHandler)
    }
}