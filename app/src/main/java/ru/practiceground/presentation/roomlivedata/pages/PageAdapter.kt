package ru.practiceground.presentation.roomlivedata.pages

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.R
import ru.practiceground.databinding.ItemLikeUnlikeBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.roomlivedata.ClickHandler
import ru.practiceground.presentation.roomlivedata.LikeableItem
import java.util.concurrent.Executors

class PageAdapter : RecyclerView.Adapter<PageAdapter.Holder>() {

    private var items = emptyList<LikeableItem>()
    private var clickHandler = ClickHandler.empty()
    private val executor = Executors.newSingleThreadExecutor()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(getBinding(parent, R.layout.item_like_unlike))
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
                data = item
                clickHandler = this@PageAdapter.clickHandler
                itemLikeBtn.setOnCheckedChangeListener { _, isChecked -> item.isLiked = isChecked }
            }
        }
    }
}