package ru.practiceground.presentation.roomlivedata.pages.favs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.databinding.FragmentPageFavsBinding
import ru.practiceground.other.extensions.string
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.roomlivedata.ViewPagerFragment
import ru.practiceground.presentation.roomlivedata.pages.PagePagingAdapter

class PageFavsFragment : BaseFragment() {

    override val viewModel: PageFavsViewModel by viewModels()

    private lateinit var binding: FragmentPageFavsBinding

    private val adapter = PagePagingAdapter()
    private val parentFragment get() = getParentFragment() as? ViewPagerFragment
    private val counter get() = parentFragment?.counter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPageFavsBinding.inflate(inflater, container, false).apply {
            adapter.setOnItemsCountChangedAction { itemCount ->
                counter?.text = itemCount.string
            }
            recView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@PageFavsFragment.adapter
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
            items.setObserver {
                adapter.submitList(it)
                binding.image.isVisible = it.isEmpty()
            }
            clickHandler.setObserver(adapter::setClickHandler)
        }
    }

    override fun onResume() {
        super.onResume()
        counter?.text = (viewModel.items.value?.size ?: 0).string
    }
}