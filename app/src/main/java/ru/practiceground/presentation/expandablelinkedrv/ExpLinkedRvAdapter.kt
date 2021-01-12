package ru.practiceground.presentation.expandablelinkedrv

import android.content.Context
import android.text.InputFilter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.databinding.ItemExpandBodyBinding
import ru.practiceground.databinding.ItemExpandTitleBinding
import ru.practiceground.other.extensions.context
import ru.practiceground.other.extensions.string

class ExpLinkedRvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<BaseExpLinkedItem>()

    private var layoutInflater: LayoutInflater? = null

    private fun getLayoutInflater(context: Context): LayoutInflater = layoutInflater ?: kotlin.run {
        layoutInflater = LayoutInflater.from(context)
        layoutInflater!!
    }

    private fun <T> MutableList<T>.replaceAll(items: Collection<T>) {
        clear()
        addAll(items)
    }

    fun setItems(items: List<BaseExpLinkedItem>) {
        this.items.replaceAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == BaseExpLinkedItem.VIEW_TYPE_TITLE) {
            ExpandTitleHolder(ItemExpandTitleBinding.inflate(getLayoutInflater(parent.context), parent, false))
        } else {
            BodyItemHolder(ItemExpandBodyBinding.inflate(getLayoutInflater(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExpandTitleHolder -> holder.bind(items[position] as ExpLinkedItem)
            is BodyItemHolder -> holder.bind(items[position] as ExpLinkedSubItem)
        }
    }

    inner class ExpandTitleHolder(private val binding: ItemExpandTitleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExpLinkedItem) = with(binding) {
            titleTv.text = item.title
            root.setOnClickListener {
                if (item.isExpanded) {
                    items.removeRange(adapterPosition + 1, item.items.size)
                    notifyItemRangeRemoved(adapterPosition + 1, item.items.size)
                } else {
                    items.addAll(adapterPosition + 1, item.items)
                    notifyItemRangeInserted(adapterPosition + 1, item.items.size)
                }
                item.isExpanded = !item.isExpanded
            }
        }

        private fun <T> MutableList<T>.removeRange(fromIndex: Int, itemCount: Int) {
            repeat(itemCount) { removeAt(fromIndex) }
        }
    }

    inner class BodyItemHolder(private val binding: ItemExpandBodyBinding) : RecyclerView.ViewHolder(binding.root) {

        private val lengthFilter = InputFilter.LengthFilter(160)

        fun bind(item: ExpLinkedSubItem) = binding.apply {
            val filters = bodyTv.filters.filter { it !is InputFilter.LengthFilter }.toTypedArray()
            if (item.state == CollapsedState.COLLAPSED) {
                bodyTv.maxLines = 10
                bodyTv.filters = arrayOf(*filters, lengthFilter)
                bodyTv.ellipsize = TextUtils.TruncateAt.END
            } else if (item.state == CollapsedState.EXPANDED) {
                bodyTv.maxLines = Int.MAX_VALUE
                bodyTv.filters = filters
                bodyTv.ellipsize = null
            }
            moreTv.setOnClickListener {
                item.state = CollapsedState.EXPANDED
                bodyTv.maxLines = Int.MAX_VALUE
                root.post {
                    moreTv.isVisible = bodyTv.layout.getEllipsisCount(bodyTv.layout.lineCount - 1) > 0
                }
            }
            titleTv.text = item.title
            bodyTv.text = item.body
            root.post {
                moreTv.isVisible = bodyTv.layout.getEllipsisCount(bodyTv.layout.lineCount - 1) > 0
            }
        }
    }
}