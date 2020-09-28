package ru.practiceground.presentation.vk.pages.news

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practiceground.R
import ru.practiceground.databinding.FragmentNewsPageBinding
import ru.practiceground.databinding.ItemStoryBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.vk.VKViewModel

class NewsPageFragment : BaseFragment() {

    override val viewModel: VKViewModel by activityViewModels()
    override val bgDrawable: Drawable? = null

    private lateinit var binding: FragmentNewsPageBinding
    private val adapter = NewsPageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container, R.layout.fragment_news_page)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recView.apply {
            adapter = this@NewsPageFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        subscribe()
    }

    private fun subscribe() {
        with(viewModel) {
            stories.setObserver(::addStories)
            newsItems.setObserver(adapter::setItems)
        }
    }

    private fun addStories(item: StoriesItem) {
        item.stories.forEach { story ->
            val itemBinding: ItemStoryBinding = getBinding(binding.storiesLl, R.layout.item_story)
            itemBinding.data = story
            itemBinding.root.setOnClickListener {
                val location = IntArray(2)
                itemBinding.avatarCard.getLocationOnScreen(location)
                item.onStoryClick(
                    story,
                    location[0].toFloat() + itemBinding.avatarCard.width.div(2),
                    location[1].toFloat() + itemBinding.avatarCard.height.div(2)
                )
            }
            binding.storiesLl.addView(itemBinding.root)
        }
    }
}