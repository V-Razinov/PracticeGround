package ru.practiceground.other.base

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

abstract class ExpandableRecViewItem(var isExpanded: Boolean = false)

abstract class ExpandableAdapter<ItemsType: ExpandableRecViewItem> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected abstract var scrollToItem: (Int) -> Unit
    protected abstract val ARROW_ROTATE_DURATION: Long
    protected abstract var items: List<ItemsType>

    protected var lastExpandedItemPosition = -1

    abstract inner class ExpandableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract val viewToClick: View
        abstract val viewToExpand: View
        abstract val viewToRotate: View?

        open fun bind(item: ItemsType) {
            viewToClick.setOnClickListener {
                item.isExpanded = !item.isExpanded

                if (lastExpandedItemPosition.run { this != -1 && this != adapterPosition } ) {
                    items[lastExpandedItemPosition].isExpanded = false
                    notifyItemChanged(lastExpandedItemPosition)
                }
                viewToRotate?.rotate(item.isExpanded)
                notifyItemChanged(adapterPosition)
            }

            if (item.isExpanded) {
                lastExpandedItemPosition = adapterPosition
                scrollToItem.invoke(adapterPosition)
            }

            viewToExpand.isVisible = item.isExpanded

            viewToRotate?.rotation = if (item.isExpanded) 180f else 0f
        }

        open fun View.rotate(isExpanded: Boolean) =
            animate().setDuration(ARROW_ROTATE_DURATION).rotation(if (isExpanded) 180f else 0f)
    }
}