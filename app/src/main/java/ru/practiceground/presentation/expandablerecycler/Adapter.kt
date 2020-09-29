package ru.practiceground.presentation.expandablerecycler

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practiceground.R
import ru.practiceground.databinding.ItemExpandableBinding
import ru.practiceground.other.base.ExpandableAdapter
import ru.practiceground.other.getBinding

class Adapter : ExpandableAdapter<MyItem>() {

    override val ARROW_ROTATE_DURATION: Long = 200L

    public override var scrollToItem: (Int) -> Unit = { }
    public override var items: List<MyItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyVH(getBinding(parent, R.layout.item_expandable))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyVH).bind(items[position])
    }

    inner class MyVH(private val binding: ItemExpandableBinding) : ExpandableViewHolder(binding.root) {

        override val viewToClick: View = binding.itemExpandableTitle
        override val viewToExpand: View = binding.itemExpandableBody
        override val viewToRotate: View? = binding.itemExpandableArrow

        override fun bind(item: MyItem) {
            super.bind(item)

        }
    }
}