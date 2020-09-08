package ru.practiceground.presentation.viewpager.pages

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.R
import ru.practiceground.databinding.ItemLikeUnlikeBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.viewpager.ClickHandler
import ru.practiceground.presentation.viewpager.LikeableItem

class PageAdapter : RecyclerView.Adapter<PageAdapter.Holder>() {

    private val items = mutableListOf<LikeableItem>()
    private var onLikeClickAction: (LikeableItem) -> Unit = {}
    private var clickHandler = ClickHandler.empty()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(getBinding(parent, R.layout.item_like_unlike))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<LikeableItem>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun setOnLikeClickAction(action: (LikeableItem) -> Unit) {
        onLikeClickAction = action
    }

    fun setClickHandler(handler: ClickHandler) {
        clickHandler = handler
    }

    inner class Holder(private val binding: ItemLikeUnlikeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LikeableItem) {
            binding.apply {
                data = item
                clickHandler = this@PageAdapter.clickHandler
                itemLikeBtn.setOnCheckedChangeListener { _, isChecked -> item.isLiked = isChecked }
            }
        }
    }
}