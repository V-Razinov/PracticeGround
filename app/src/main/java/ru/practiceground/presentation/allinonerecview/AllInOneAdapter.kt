package ru.practiceground.presentation.allinonerecview

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_expandable1.view.*
import kotlinx.android.synthetic.main.item_horizontal_rec_view.view.*
import ru.practiceground.R
import ru.practiceground.other.getView
import ru.practiceground.presentation.base.BaseRecViewItem

class AllInOneAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ARROW_ROTATE_DURATION: Long = 200L

    private val ITEM_TYPE_NOTHING = -1
    private val ITEM_TYPE_EXPANDABLE = 0
    private val ITEM_TYPE_HORIZONTAL_REC_VIEW = 1
    private val ITEM_TYPE_SWIPE = 2

    private var scrollToItem: (Int) -> Unit = { }
    private var lastExpandedItemPosition = -1

    var items = emptyList<BaseRecViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is ExpandableItem -> ITEM_TYPE_EXPANDABLE
        is HorizontalRecViewItem -> ITEM_TYPE_HORIZONTAL_REC_VIEW
        is SwipeItem -> ITEM_TYPE_SWIPE
        else -> ITEM_TYPE_NOTHING
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_EXPANDABLE ->
                ExpandableViewHolder(getView(parent, R.layout.item_expandable1))
            ITEM_TYPE_HORIZONTAL_REC_VIEW ->
                HorizontalRecViewHolder(getView(parent, R.layout.item_horizontal_rec_view))
            ITEM_TYPE_SWIPE ->
                SwipeViewHolder(getView(parent, R.layout.item_swipe))
            ITEM_TYPE_NOTHING ->
                error("пиздец")//todo
            else ->
                error("пиздец")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExpandableViewHolder -> holder.bind(items[position] as ExpandableItem)
            is HorizontalRecViewHolder -> holder.bind(items[position] as HorizontalRecViewItem)
        }
    }

    inner class SwipeViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: SwipeItem) {
            view.apply {

            }
        }
    }

    inner class ExpandableViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ExpandableItem) {
            view.apply {
                title_tv.text = item.title
                body_tv.text = item.body
                title_tv.setOnClickListener {
                    item.isExpanded = !item.isExpanded

                    if (lastExpandedItemPosition.run { this != -1 && this != adapterPosition }) {
                        (items[lastExpandedItemPosition] as? ExpandableItem)?.isExpanded = false
                        notifyItemChanged(lastExpandedItemPosition)
                    }

                    arrow_iv.rotate(item.isExpanded)
                    notifyItemChanged(adapterPosition)
                }

                if (item.isExpanded) {
                    lastExpandedItemPosition = adapterPosition
                    scrollToItem.invoke(adapterPosition)
                }

                body_tv.isVisible = item.isExpanded

                arrow_iv.rotation = if (item.isExpanded) 180f else 0f
            }
        }
    }

    inner class HorizontalRecViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: HorizontalRecViewItem) {
            view.apply {

                horizontal_rec_view.apply {
                    layoutManager = LinearLayoutManager(context).apply {
                        orientation = LinearLayoutManager.HORIZONTAL
                    }
                    adapter = HorizontalGifsAdapter(item.items)
                }
                horizontal_title_tv.setOnClickListener {
                    item.isExpanded = !item.isExpanded

                    if (lastExpandedItemPosition.run { this != -1 && this != adapterPosition }) {
                        (items[lastExpandedItemPosition] as? ExpandableItem)?.isExpanded = false
                        notifyItemChanged(lastExpandedItemPosition)
                    }

                    horizontal_arrow_iv.rotate(item.isExpanded)
                    notifyItemChanged(adapterPosition)
                }

                if (item.isExpanded) {
                    lastExpandedItemPosition = adapterPosition
                    scrollToItem.invoke(adapterPosition)
                }

                horizontal_rec_view.isVisible = item.isExpanded

                horizontal_arrow_iv.rotation = if (item.isExpanded) 180f else 0f
            }
        }
    }

    private fun View.rotate(isExpanded: Boolean) =
        animate().setDuration(ARROW_ROTATE_DURATION).rotation(if (isExpanded) 180f else 0f)
}