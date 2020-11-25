package ru.practiceground.presentation.vk.pages.news

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practiceground.R
import ru.practiceground.databinding.ItemCreatePostBinding
import ru.practiceground.databinding.ItemNewsPostBinding
import ru.practiceground.other.extensions.replaceAll
import ru.practiceground.other.extensions.replaceCompoundDrawablesEnd
import ru.practiceground.other.extensions.string
import ru.practiceground.other.getDrawable
import ru.practiceground.presentation.vk.pages.news.NewsPageAdapterBaseItem.Companion.ITEM_TYPE_CREATE_POST
import ru.practiceground.presentation.vk.pages.news.NewsPageAdapterBaseItem.Companion.ITEM_TYPE_POST

abstract class BaseViewHolder<Item : NewsPageAdapterBaseItem>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: Item)
}

class NewsPageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<NewsPageAdapterBaseItem>()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_TYPE_CREATE_POST -> CreatePostHolder(ItemCreatePostBinding.inflate(layoutInflater, parent, false))
            ITEM_TYPE_POST -> PostHolder(ItemNewsPostBinding.inflate(layoutInflater, parent, false))
            else -> throw Exception("NewsPageAdapter: viewType")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder<NewsPageAdapterBaseItem>).bind(items[position])
    }

    fun setItems(newItems: List<NewsPageAdapterBaseItem>) {
        items.replaceAll(newItems)
        notifyDataSetChanged()
    }

    class CreatePostHolder(private val binding: ItemCreatePostBinding) : BaseViewHolder<CreatePostItem>(binding.root) {

        override fun bind(item: CreatePostItem) {
            binding.apply {
                Glide.with(avatar).load(item.avatarLink).into(avatar)
                createPost.setOnClickListener { item.onCreateClick() }
                clip.setOnClickListener { item.onClipClick() }
                live.setOnClickListener { item.onLiveClick() }
            }
        }
    }

    class PostHolder(private val binding: ItemNewsPostBinding) : BaseViewHolder<NewsPostItem>(binding.root) {

        override fun bind(item: NewsPostItem) {
            binding.apply {
                Glide.with(binding.root).apply {
                    load(item.avatarLink).into(avatar)
                    load(item.imageLink).into(image)
                }
                author.apply {
                    replaceCompoundDrawablesEnd(if (item.isVerified) getDrawable(R.drawable.ic_round_check_24) else null)
                    text = item.author
                }
                timePassed.text = item.timePassed
                text.text = item.text
                likeTv.text = item.likesAmount
                commentsTv.text = item.commentsAmount
                repostsTv.text = item.rePostsAmount
                views.apply {
                    text = item.viewsAmount
                    isInvisible = item.viewsAmount == 0.string
                }
                menu.setOnClickListener { menu.showPopUpMenu(item.menuActions) }
                like.setOnClickListener {
                    item.isLiked = !item.isLiked
                    val drawable = getDrawable(if (item.isLiked) R.drawable.ic_heart_filled_24 else R.drawable.ic_heart_24)
                    likeTv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                }
            }
        }

        private fun View.showPopUpMenu(menuActions: List<MenuAction>) {
            val popMenu = PopupMenu(ContextThemeWrapper(context, R.style.PopupMenuTheme), this)
            val items = listOf("Video", "Document", "Poll", "Map", "Product")
            menuActions.forEach { item ->
                popMenu.menu.add(Menu.NONE, Menu.NONE, item.id, item.title)
            }
            popMenu.setOnMenuItemClickListener { menuItem ->
                Toast.makeText(context, items[menuItem.itemId], Toast.LENGTH_LONG).show()
                true
            }
            popMenu.show()
        }
    }
}