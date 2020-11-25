package ru.practiceground.presentation.roomlivedata.pages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemLikeUnlikeBinding
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem

class PageAdapter : RecyclerView.Adapter<PageAdapter.Holder>() {

    private var items = emptyList<LikeableItem>()
    private var clickHandler = ClickHandler.empty()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemLikeUnlikeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<LikeableItem>, diffs: DiffUtil.DiffResult) {
        items = newItems
        diffs.dispatchUpdatesTo(this)
    }

    fun setClickHandler(handler: ClickHandler) {
        clickHandler = handler
    }

    inner class Holder(private val binding: ItemLikeUnlikeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LikeableItem) {
            binding.apply {
                itemTextTv.text = item.text
                itemLikeBtn.apply {
                    isChecked = item.isLiked
                    setOnClickListener { clickHandler.onLikeClick(item) }
                    setOnCheckedChangeListener { _, isChecked -> item.isLiked = isChecked }
                }
                itemDeleteIv.setOnClickListener { clickHandler.onDeleteClick(item) }
            }
        }
    }
}