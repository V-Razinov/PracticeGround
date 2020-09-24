package ru.practiceground.presentation.vk.pages.news

import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.App
import ru.practiceground.R
import ru.practiceground.databinding.ItemCreatePostBinding
import ru.practiceground.databinding.ItemNewsPostBinding
import ru.practiceground.other.getBinding

class NewsPageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<NewsPageAdapterBaseItem>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_CREATE_POST -> CreatePostHolder(getBinding(parent, R.layout.item_create_post))
            ITEM_TYPE_POST -> PostHolder(getBinding(parent, R.layout.item_news_post))
            else -> throw Exception("NewsPageAdapter: viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CreatePostHolder -> holder.bind(items[position] as CreatePostItem)
            is PostHolder -> holder.bind(items[position] as NewsPostItem)
        }
    }

    fun setItems(newItems: List<NewsPageAdapterBaseItem>) {
        items.apply {
            clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    class CreatePostHolder(private val binding: ItemCreatePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(createPostItem: CreatePostItem) {
            binding.data = createPostItem
        }
    }

    class PostHolder(private val binding: ItemNewsPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsPostItem: NewsPostItem) {
            binding.data = newsPostItem
            binding.like.setOnClickListener {
                newsPostItem.isLiked = !newsPostItem.isLiked

                binding.likeTv.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(
                    App.context.resources,
                    if (newsPostItem.isLiked) R.drawable.ic_heart_filled_24 else R.drawable.ic_heart_24,
                    null
                ), null, null, null)
            }
        }
    }
}