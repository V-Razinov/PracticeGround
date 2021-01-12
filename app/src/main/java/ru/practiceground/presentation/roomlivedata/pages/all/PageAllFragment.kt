package ru.practiceground.presentation.roomlivedata.pages.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.databinding.FragmentPageAllBinding
import ru.practiceground.other.extensions.string
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.roomlivedata.ViewPagerFragment
import ru.practiceground.presentation.roomlivedata.pages.PagePagingAdapter

class PageAllFragment : BaseFragment() {

    override val viewModel: PageAllViewModel by viewModels()

    private lateinit var binding: FragmentPageAllBinding

    private val adapter = PagePagingAdapter()
    private val parentFragment get() = getParentFragment() as? ViewPagerFragment
    private val counter get() = parentFragment?.counter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPageAllBinding.inflate(inflater, container, false).apply {
            adapter.setOnItemsCountChangedAction { itemCount ->
                counter?.text = itemCount.string
            }
            recView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@PageAllFragment.adapter
                post {
                    parentFragment?.let { viewPagerFragment ->
                        updatePadding(bottom = viewPagerFragment.fab.run { marginBottom + height })
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            items.observe {
                adapter.submitList(it)
                binding.image.isVisible = it.isEmpty()
            }
            clickHandler.observe(adapter::setClickHandler)
        }
    }

    internal fun setCounter() {
        counter?.text = (viewModel.items.value?.size ?: 0).string
    }
}