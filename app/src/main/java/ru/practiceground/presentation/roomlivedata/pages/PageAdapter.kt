package ru.practiceground.presentation.roomlivedata.pages

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_like_unlike.view.*
import ru.practiceground.R
import ru.practiceground.other.getView
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem

class PageAdapter : RecyclerView.Adapter<PageAdapter.Holder>() {

    private var items = emptyList<LikeableItem>()
    private var clickHandler = ClickHandler.empty()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(getView(parent, R.layout.item_like_unlike))
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

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(item: LikeableItem) {
            view.apply {
                item_text_tv.text = item.text
                item_like_btn.apply {
                    isChecked = item.isLiked
                    setOnClickListener { clickHandler.onLikeClick(item) }
                    setOnCheckedChangeListener { _, isChecked -> item.isLiked = isChecked }
                }
                item_delete_iv.setOnClickListener { clickHandler.onDeleteClick(item) }
            }
        }
    }
}