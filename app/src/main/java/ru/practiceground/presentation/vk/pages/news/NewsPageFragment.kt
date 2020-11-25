package ru.practiceground.presentation.vk.pages.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.practiceground.databinding.FragmentNewsPageBinding
import ru.practiceground.databinding.ItemStoryBinding
import ru.practiceground.other.extensions.float
import ru.practiceground.presentation.base.BaseFragment
import ru.practiceground.presentation.vk.VKViewModel

class NewsPageFragment : BaseFragment() {

    override val viewModel: VKViewModel by activityViewModels()

    private lateinit var binding: FragmentNewsPageBinding
    private val adapter = NewsPageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsPageBinding.inflate(layoutInflater, container, false)
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
            val view = ItemStoryBinding.inflate(layoutInflater, null, false).apply {
                Glide.with(this.root).load(story.avatarLink).into(avatar)
                itemText.text = story.text
                root.setOnClickListener {
                    val location = IntArray(2)
                    avatarCard.getLocationOnScreen(location)
                    item.onStoryClick(
                        story,
                        location[0].float + avatarCard.width.div(2),
                        location[1].float + avatarCard.height.div(2)
                    )
                }
            }.root
            binding.storiesLl.addView(view)
        }
    }
}