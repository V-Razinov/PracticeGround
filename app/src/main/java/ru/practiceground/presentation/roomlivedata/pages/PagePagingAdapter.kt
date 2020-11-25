package ru.practiceground.presentation.roomlivedata.pages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemLikeUnlikeBinding
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem

class PagePagingAdapter: PagedListAdapter<LikeableItem, PagePagingAdapter.Holder>(diffCallback) {

    private var clickHandler = ClickHandler.empty()
    private var onItemCountChangedAction: (Int) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemLikeUnlikeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    fun setClickHandler(handler: ClickHandler) {
        clickHandler = handler
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        onItemCountChangedAction(count)
        return count
    }

    fun setOnItemsCountChangedAction(action: (Int) -> Unit) {
        onItemCountChangedAction = action
    }

    inner class Holder(private val binding: ItemLikeUnlikeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LikeableItem) = binding.apply {
            itemTextTv.text = item.text
            itemLikeBtn.apply {
                isChecked = item.isLiked
                setOnClickListener { clickHandler.onLikeClick(item) }
                setOnCheckedChangeListener { _, isChecked -> item.isLiked = isChecked }
            }
            itemDeleteIv.setOnClickListener { clickHandler.onDeleteClick(item) }
        }
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<LikeableItem>() {

            override fun areItemsTheSame(oldItem: LikeableItem, newItem: LikeableItem): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LikeableItem, newItem: LikeableItem): Boolean = oldItem.compareContent(newItem)
        }
    }
}